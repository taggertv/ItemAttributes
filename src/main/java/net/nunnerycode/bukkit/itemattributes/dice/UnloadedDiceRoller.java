package net.nunnerycode.bukkit.itemattributes.dice;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomOperator;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import java.util.Arrays;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.api.dice.DiceRoller;
import org.apache.commons.lang.math.RandomUtils;

public class UnloadedDiceRoller implements DiceRoller {

	private final List<Double> acceptableDiceSizes;
	private final CustomOperator diceRollOperator;

	public UnloadedDiceRoller(Double... diceSizes) {
		acceptableDiceSizes = Arrays.asList(diceSizes);
		diceRollOperator = new CustomOperator(";", false, 6, 1) {
			@Override
			protected double applyOperation(double[] doubles) {
				if (!acceptableDiceSizes.contains(doubles[0])) {
					return 0.0;
				}
				return (RandomUtils.nextDouble() * doubles[0] + 1);
			}
		};
	}

	@Override
	public String getFormula(double numberOfDice, double numberOfSides, double modifier) {
		String format = "%sd%s%s%s";
		return String.format(format, numberOfDice, numberOfSides, (modifier >= 0) ? "+" : "", modifier);
	}

	@Override
	public double getDiceRoll(String formula) {
		try {
			Calculable calculable = new ExpressionBuilder(formula).withOperation(diceRollOperator).build();
			return calculable.calculate();
		} catch (UnknownFunctionException e) {
			e.printStackTrace();
		} catch (UnparsableExpressionException e) {
			e.printStackTrace();
		}
		return 0D;
	}

	@Override
	public Double[] getAcceptableDiceSizes() {
		return acceptableDiceSizes.toArray(new Double[acceptableDiceSizes.size()]);
	}

	@Override
	public void addAcceptableDiceSize(Double d) {
		acceptableDiceSizes.add(d);
	}

	@Override
	public void removeAcceptableDiceSize(Double d) {
		acceptableDiceSizes.remove(d);
	}
}
