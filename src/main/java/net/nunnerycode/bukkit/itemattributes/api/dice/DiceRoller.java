package net.nunnerycode.bukkit.itemattributes.api.dice;

public interface DiceRoller {

	public String getFormula(double numberOfDice, double numberOfSides, double modifier);

	public double getDiceRoll(String formula);

	public Double[] getAcceptableDiceSizes();

	public void addAcceptableDiceSize(Double d);

	public void removeAcceptableDiceSize(Double d);

}
