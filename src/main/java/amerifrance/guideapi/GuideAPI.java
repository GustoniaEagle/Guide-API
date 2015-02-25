package amerifrance.guideapi;

import amerifrance.guideapi.items.ItemsRegistry;
import amerifrance.guideapi.pages.PageUnlocText;
import amerifrance.guideapi.proxies.CommonProxy;
import amerifrance.guideapi.test.TestBooks;
import amerifrance.guideapi.util.serialization.BookCreator;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.File;

@Mod(modid = ModInformation.ID, name = ModInformation.NAME, version = ModInformation.VERSION, dependencies = ModInformation.DEPEND)
public class GuideAPI {

    public static CreativeTabs tabGuide = new CreativeTabs(ModInformation.ID + ".creativeTab") {
        @Override
        public ItemStack getIconItemStack() {
            return new ItemStack(Items.book);
        }

        @Override
        public Item getTabIconItem() {
            return Items.book;
        }
    };

    @Mod.Instance
    public static GuideAPI instance;
    @SidedProxy(clientSide = ModInformation.CLIENTPROXY, serverSide = ModInformation.COMMONPROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;
        ConfigHandler.init(new File(event.getModConfigurationDirectory() + "/Guide-API" + ".cfg"));
        ItemsRegistry.registerItems();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.registerBooks();
        GameRegistry.addShapelessRecipe(GuideRegistry.getItemStackForBook(TestBooks.testBook1), new Object[]{new ItemStack(Items.book), new ItemStack(Items.apple)});
        GameRegistry.addShapelessRecipe(GuideRegistry.getItemStackForBook(TestBooks.testBook2), new Object[]{new ItemStack(Items.book), new ItemStack(Items.arrow)});
        System.out.println(PageUnlocText.class.getSimpleName());
        GsonBuilder gsonBuilder = new GsonBuilder();
        BookCreator.registerCustomSerializers(gsonBuilder);
        //try {
        //    FileWriter writer = new FileWriter("TestBook.json");
        //    writer.write(gsonBuilder.setPrettyPrinting().create().toJson(TestBooks.testBook1));
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        GuideRegistry.registerBook(BookCreator.createBookFromJson(gsonBuilder, "TestBook.json"));
    }
}
