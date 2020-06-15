package ru.kpfu.itis.group_804.project.service.interfaces;

import ru.kpfu.itis.group_804.project.dto.DialogDto;
import ru.kpfu.itis.group_804.project.models.Dialog;

import java.util.List;
import java.util.Optional;

public interface DialogService {
    List<DialogDto> getAllDialogs(Long id);
    Long createDialog(Long writer, Long goal);
    Optional<Dialog> getDialog(Long current, Long friend);
    Optional<Dialog> getDialogById(Long dialogId);
}
