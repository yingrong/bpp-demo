package com.example.bpp.task;

import com.example.bpp.bo.CalculatedTaskBo;
import com.example.bpp.service.TaskService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculatedTask {
    private final TaskService taskService;

    public CalculatedTask(TaskService taskService) {
        this.taskService = taskService;
    }

    public void execute() {
        CalculatedTaskBo calculatedTaskBo = taskService.queryIndexCalculatedData();
        for (String targetIndex : calculatedTaskBo.getSourceMap().keySet()) {
            calculatedTaskBo.setTargetIndex(targetIndex);
            try {
                taskService.indexCalculatedDataSave(calculatedTaskBo);
            } catch (RuntimeException e) {
                log.error("任务执行异常", e);
                return;
            }
        }
    }
}