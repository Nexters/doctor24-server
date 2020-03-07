package me.nexters.doctor24.batch.config.listener;

import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.InvokeFailureContext;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HospitalDetailFailureStepListener implements StepExecutionListener {
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("재배치 실패건수 : " + InvokeFailureContext.size());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("재배치 작업 이후 : " + InvokeFailureContext.size());
		return null;
	}
}
