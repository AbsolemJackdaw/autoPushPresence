package be.jackdaw.fatchicken.event;

import be.jackdaw.fatchicken.FatChickenMod;
import be.jackdaw.fatchicken.capability.Fat;
import be.jackdaw.fatchicken.entity.FatChicken;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = FatChickenMod.MODID)
public class InteractAnimalEvent {

    @SubscribeEvent
    public static void interact(PlayerInteractEvent.EntityInteract event) {

        if (event.getItemStack().getItem().equals(Items.SWEET_BERRIES)) {
            if (event.getTarget() instanceof Animal animal) {
                Fat.get(animal).ifPresent(data -> {
                    if (event.getWorld().isClientSide) {
                        event.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, event.getTarget().getRandomX(1.0D), event.getTarget().getRandomY() + 0.5D, event.getTarget().getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
                    } else {
                        data.feed();
                        event.getPlayer().swing(event.getHand());
                        if (!event.getPlayer().isCreative())
                            event.getItemStack().shrink(1);

                        if (data.isFat()) {
                            FatChicken fatchicken = FatChicken.make(animal.level);
                            fatchicken.setPos(animal.position());
                            fatchicken.setYRot(animal.getYRot());
                            fatchicken.setXRot(animal.getXRot());
                            animal.remove(Entity.RemovalReason.DISCARDED);
                            event.getPlayer().level.addFreshEntity(fatchicken);
                        }
                    }
                });
            }
        }
    }
}
