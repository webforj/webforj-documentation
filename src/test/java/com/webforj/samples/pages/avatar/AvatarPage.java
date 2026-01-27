package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AvatarPage {

    private static final String ROUTE = "avatar";

    private final Locator panel;
    private final Locator projectHeader;
    private final Locator teamLabel;
    private final Locator sarahAvatar;
    private final Locator marcusAvatar;
    private final Locator elenaAvatar;
    private final Locator davidAvatar;
    private final Locator inviteRow;
    private final Locator dialog;

    public AvatarPage(Page page) {
        this.panel = page.locator(".avatar-demo__panel");
        this.projectHeader = page.locator(".avatar-demo__project-header");
        this.teamLabel = page.getByText("Team", new Page.GetByTextOptions().setExact(true));
        this.sarahAvatar = page.locator("dwc-avatar[tooltip='Sarah Chen']");
        this.marcusAvatar = page.locator("dwc-avatar[tooltip='Marcus Johnson']");
        this.elenaAvatar = page.locator("dwc-avatar[tooltip='Elena Rodriguez']");
        this.davidAvatar = page.locator("dwc-avatar[tooltip='David Kim']");
        this.inviteRow = page.getByText("Invite Member");
        this.dialog = page.locator("dwc-dialog");
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getPanel() {
        return panel;
    }

    public Locator getProjectHeader() {
        return projectHeader;
    }

    public Locator getTeamLabel() {
        return teamLabel;
    }

    public Locator getSarahAvatar() {
        return sarahAvatar;
    }

    public Locator getMarcusAvatar() {
        return marcusAvatar;
    }

    public Locator getElenaAvatar() {
        return elenaAvatar;
    }

    public Locator getDavidAvatar() {
        return davidAvatar;
    }

    public Locator getInviteRow() {
        return inviteRow;
    }

    public Locator getDialog() {
        return dialog;
    }
}
