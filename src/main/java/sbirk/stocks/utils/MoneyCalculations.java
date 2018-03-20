package sbirk.stocks.utils;

import java.math.BigDecimal;

public class MoneyCalculations {
	
	private BigDecimal amountOne;
	private BigDecimal amountTwo;
	
	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	
	private static int DECIMALS = 2;
	private static int EXTRA_DECIMALS = 4;
	
	public MoneyCalculations (BigDecimal inputAmountOne, BigDecimal inputAmountTwo) {
		this.amountOne = inputAmountOne;
		this.amountTwo = inputAmountTwo;
	}
	
	public BigDecimal rounded(BigDecimal input, boolean extraDigits) {
		if (extraDigits) {
			return input.setScale(EXTRA_DECIMALS, ROUNDING_MODE);
		}
		return input.setScale(DECIMALS, ROUNDING_MODE);
	}
	
	public BigDecimal getSum () {
		return amountOne.add(amountTwo);
	}
	
	public BigDecimal getDifference () {
		return amountOne.subtract(amountTwo);
	}
	
	public BigDecimal getProduct () {
		BigDecimal product = amountOne.multiply(amountTwo);
		return rounded(product, true);
	}
	
	public BigDecimal getQuotient () {
		BigDecimal quotient = amountOne.divide(amountTwo);
		return rounded(quotient, true);
	}
	
	public BigDecimal getAverage () {
		BigDecimal avg = this.getSum().divide(new BigDecimal(2));
		return rounded(avg, true);
	}
	
	public BigDecimal getPercentage () {
		BigDecimal result = amountOne.multiply(amountTwo.divide(new BigDecimal (100)));
		return rounded(result, true);
	}
	
	public BigDecimal getPercentChange () {
		BigDecimal percentChange = getDifference().divide(amountOne, EXTRA_DECIMALS, ROUNDING_MODE);
		return rounded(percentChange.multiply(new BigDecimal(100)), false);
	}
}
