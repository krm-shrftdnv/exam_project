package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.group_804.project.config.security.details.UserDetailsImpl;
import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.dto.MessageDto;
import ru.kpfu.itis.group_804.project.dto.UserDto;
import ru.kpfu.itis.group_804.project.models.Dialog;
import ru.kpfu.itis.group_804.project.models.Message;
import ru.kpfu.itis.group_804.project.service.interfaces.DialogService;
import ru.kpfu.itis.group_804.project.service.interfaces.MessagesService;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    MessagesService messagesService;
    @Autowired
    DialogService dialogService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat{dialog-id}")
    public String getChatWithUser(@PathVariable("dialog-id") Long dialogId, Model model, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        if (dialogService.getDialogById(dialogId).isPresent()) {
            Dialog dialog = dialogService.getDialogById(dialogId).get();
            if (dialog.getMember1().getId().equals(current.getId()) || dialog.getMember2().getId().equals(current.getId())) {
                model.addAttribute("current", current);
                List<Message> messages = messagesService.getAllMessages(dialogId);
                List<MessageDto> messagesDto = MessageDto.from(messages);
                model.addAttribute("messages", messagesDto);
                DialogDto dialogDto = DialogDto.from(dialog);
                model.addAttribute("dialog", dialogDto);
                UserDto other = UserDto.from((
                        dialog.getMember1()
                                .getId()
                                .equals(current.getId())) ? dialog.getMember2() : dialog.getMember1()
                );
                model.addAttribute("other", other);
                return "chat";
            }
        }
        return "redirect:/dialogs";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/chat{dialog-id}/sendMessage")
    public String sendMessage(@PathVariable("dialog-id") Long dialogId, Authentication authentication, @RequestParam(name = "text")String text) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDto current = UserDto.from(userDetails.getUser());
        if (dialogService.getDialogById(dialogId).isPresent()) {
            Dialog dialog = dialogService.getDialogById(dialogId).get();
            if (dialog.getMember1().getId().equals(current.getId()) || dialog.getMember2().getId().equals(current.getId())) {
                messagesService.sendMessage(dialogId, text, current.getId(), current.getNickname());
            }
        }
        return "redirect:/chat" + dialogId;
    }

}
