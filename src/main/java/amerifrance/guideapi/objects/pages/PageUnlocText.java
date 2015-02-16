package amerifrance.guideapi.objects.pages;

import amerifrance.guideapi.gui.GuiBase;
import amerifrance.guideapi.objects.Book;
import amerifrance.guideapi.objects.abstraction.AbstractCategory;
import amerifrance.guideapi.objects.abstraction.AbstractEntry;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

public class PageUnlocText extends PageBase {

    public String unlocText;
    public boolean useUnicode;

    public PageUnlocText(String unlocText, boolean useUnicode) {
        this.unlocText = unlocText;
        this.useUnicode = useUnicode;
    }

    @Override
    public void draw(Book book, AbstractCategory category, AbstractEntry entry, int guiLeft, int guiTop, int mouseX, int mouseY, GuiBase guiBase, FontRenderer fontRenderer) {
        if (useUnicode) fontRenderer.setUnicodeFlag(true);
        fontRenderer.drawSplitString(StatCollector.translateToLocal(unlocText), guiLeft + 37, guiTop + 12, 4 * guiBase.xSize / 6, 0);
        fontRenderer.setUnicodeFlag(false);
    }
}