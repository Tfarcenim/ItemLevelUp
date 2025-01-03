package tfar.itemlevelup.data;

public record Action(String name) {

    public static final Action ATTACK = new Action("attack");
    public static final Action MINE_BLOCK = new Action("mine_block");
    public static final Action BLOCK_DAMAGE = new Action("block_damage");
}
