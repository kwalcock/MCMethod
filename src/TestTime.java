import org.junit.Assert;

import java.util.Random;

import org.junit.Test;

public class TestTime {
	protected int count = 1000;
	
	protected long time(Discrepancy discrepancy) {
		long startTime = System.nanoTime();

		for (int i = 0; i < count; i++)
			discrepancy.next();
		
		long endTime = System.nanoTime();

		discrepancy.clear();
		return endTime - startTime;
	}
	
	@Test
	public void testTime() {
		Random randomA = new Random(1234);
		Random randomB = new Random(4321);
		double a = randomA.nextDouble();
		double b = randomB.nextDouble();
		
		Discrepancy[] discrepancies = new Discrepancy[] {
			new Discrepancy1(a, b),
			new Discrepancy1Fast(a, b),
			new Discrepancy2(a, b),
			new Discrepancy2Fast(a, b),
			new Discrepancy3(a, b),
			new Discrepancy3Fast(a, b),
			new PolarDiscrepancy(a, b),
			new PolarDiscrepancySorted(a, b),
			new PolarDiscrepancyLinked(a, b)
		};
		
		for (Discrepancy discrepancy: discrepancies) {
			long duration = time(discrepancy);
			System.out.println(discrepancy.getClass().getSimpleName() + "\t" + duration);
		}
		
		Assert.assertTrue(true);
	}
}
