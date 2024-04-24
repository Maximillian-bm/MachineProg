package machineprog2.kortspilgui.util;

import javafx.scene.effect.InnerShadow;

public class Effects {
    public static InnerShadow getInnerShadowEffect() {
        InnerShadow effect = new InnerShadow();
        effect.setChoke(0);
        effect.setWidth(35);
        effect.setHeight(35);
        effect.setRadius(20);
        effect.setOffsetX(0);
        effect.setOffsetY(0);
        return effect;
    }
}
