package hys.timetable;

import org.testng.annotations.Test;

import com.hys.common.utils.Loggers;

public class FloatTest {

	@Test
	public void t1() {
		float count = 1.5f;
		int i1 = 3;
		float avg = 1.0f * i1 / 2;
		Loggers.log.debug("");
		Loggers.log.debug("{}", i1 > count);
		Loggers.log.debug("{}", count > i1);
		Loggers.log.debug("{}", avg);
		Loggers.log.debug("{}", Math.round(2.1f));
		Loggers.log.debug("{}", Double.valueOf(Math.ceil(2.1f)).intValue());
	}
}
