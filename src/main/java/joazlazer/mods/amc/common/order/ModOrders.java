package joazlazer.mods.amc.common.order;

import joazlazer.mods.amc.api.order.OrderBase;
import joazlazer.mods.amc.common.reference.Reference;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModOrders {
    @GameRegistry.ObjectHolder("amc:arcana")
    public static final OrderBase ARCANA = null;

    @GameRegistry.ObjectHolder("amc:alchimia")
    public static final OrderBase ALCHIMIA = null;

    @GameRegistry.ObjectHolder("amc:iracundia")
    public static final OrderBase IRACUNDIA = null;

    @GameRegistry.ObjectHolder("amc:praesidia")
    public static final OrderBase PRAESIDIA = null;

    @GameRegistry.ObjectHolder("amc:praecanta")
    public static final OrderBase PRAECANTA = null;

    @Mod.EventBusSubscriber(modid = Reference.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerOrders(final RegistryEvent.Register<OrderBase> event) {
            final OrderBase[] orders = {
                    new OrderArcana(),
                    new OrderAlchimia(),
                    new OrderIracundia(),
                    new OrderPraecanta(),
                    new OrderPraesidia()
            };

            final IForgeRegistry<OrderBase> registry = event.getRegistry();
            for (final OrderBase order : orders) {
                registry.register(order);
            }
        }
    }
}
