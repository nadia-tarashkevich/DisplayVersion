package org.jetbrains.support;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.util.ui.UIUtil;
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

        int iconHeight = 16;
        int maxImageWidth = 200;
        int horizontalGap = 2;

        Font font = new Font("Arial", Font.PLAIN, 6);
        Color textColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultForeground();
        Color bgColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultBackground();
        BufferedImage image = UIUtil.createImage(maxImageWidth, iconHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics metrics = g.getFontMetrics(font);
        int iconWidth = (metrics.stringWidth(iconText) + horizontalGap * 2) * 2;// 2x default scaling for TYPE_INT_ARGB images

        g.setColor(bgColor);
        g.fillRect(0, 0, iconWidth, iconHeight);
        g.setFont(font);
        g.setColor(textColor);

        g.drawString(iconText, horizontalGap, font.getSize());
        g.dispose();

        ImageIcon imageIcon = new ImageIcon(image.getSubimage(0, 0, iconWidth, iconHeight));
        return imageIcon;
    }
}
