package me.nexters.doctor24.batch.config.listener;

import java.util.concurrent.TimeUnit;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HospitalStepListener implements StepExecutionListener {
	private long startTime;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		startTime = System.nanoTime();
		log.info(stepExecution.getStepName() + "시작");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info(stepExecution.getStepName() + " 완료");
		log.info("총 Read 갯수 : " + stepExecution.getReadCount());
		log.info("총 Commit 갯수 : " + stepExecution.getCommitCount());
		long elapsedTime = TimeUnit.MINUTES.convert((System.nanoTime() - startTime), TimeUnit.NANOSECONDS);
		log.info("소요시간 : " + elapsedTime + "분");
		return null;
	}
}
