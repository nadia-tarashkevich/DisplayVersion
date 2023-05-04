package org.jetbrains.support;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayVersion extends com.intellij.openapi.actionSystem.AnAction{
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // This action does nothing
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setIcon(getIcon());
    }

    /***
     * Generates an icon 16x(80-120) containing text "IDE name + build version"
     * @return icon type
     */
    private Icon getIcon() {
        ApplicationInfo appInfo = ServiceManager.getService(ApplicationInfo.class);
        String version = appInfo.getFullVersion();
        String build = appInfo.getBuild().asString();
        String iconText = version + " " + build;


        // set up font
        EditorColorsScheme colorsScheme = EditorColorsManager.getInstance().getGlobalScheme();
        String fontName = colorsScheme.getFont(EditorFontType.PLAIN).getFontName();
        Font font = new Font(fontName, Font.PLAIN, 12);

        // font color
        Color textColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color bgColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultBackground();

        // icon size
        int iconHeight = 16;
        int maxIconWidth = 200;
        int horizontalGap = 2;


        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage bufferedImage = gc.createCompatibleImage(maxIconWidth, iconHeight);
        Graphics2D g = bufferedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics metrics = g.getFontMetrics(font);
        int iconWidth = metrics.stringWidth(iconText) + horizontalGap * 2;

        // draw the icon
        g.setColor(bgColor);
        g.fillRect(0, 0, iconWidth, iconHeight);
        g.setFont(font);
        g.setColor(textColor);
        g.drawString(iconText, horizontalGap, font.getSize());
        g.dispose();

        return new ImageIcon(bufferedImage.getSubimage(0, 0, iconWidth, iconHeight));
    }
}
