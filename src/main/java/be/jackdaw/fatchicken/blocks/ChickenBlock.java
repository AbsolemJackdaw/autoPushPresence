package be.jackdaw.fatchicken.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChickenBlock extends Block {
    private static final VoxelShape body = Shapes.create(new AABB(0.0625 * 4d, 0.0625D, 0.0625d * 3, 0.0625d * 12, 0.0625d * 6, 0.0625d * 13));
    private static final VoxelShape board = Shapes.create(new AABB(0.0625 * 2d, 0.0D, 0.0625d * 3, 0.0625d * 14, 0.0625d * 1, 0.0625d * 14));
    private static final VoxelShape leg_r = Shapes.create(new AABB(0.0625 * 2, 0.0625 * 2D, 0.0625d * 4, 0.0625d * 4, 0.0625d * 6, 0.0625d * 8));
    private static final VoxelShape leg_l = Shapes.create(new AABB(0.0625 * 12, 0.0625 * 2D, 0.0625d * 4, 0.0625d * 14, 0.0625d * 6, 0.0625d * 8));
    private static final VoxelShape chicken = Shapes.or(body, board, leg_r, leg_l);

    public ChickenBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return chicken;
    }

    @Override
    public VoxelShape getVisualShape(BlockState p_60479_, BlockGetter p_60480_, BlockPos p_60481_, CollisionContext p_60482_) {
        return chicken;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return chicken;
    }
}
