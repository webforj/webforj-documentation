package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Img;
import com.webforj.component.icons.Icon;
import com.webforj.samples.pages.AbstractPage;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.utils.NodeNameUtils;
import com.webforj.samples.utils.WebforjLocator;
import com.webforj.samples.views.avatar.AvatarView;

import java.util.List;
import java.util.stream.IntStream;

public class AvatarPage extends AbstractPage {

  public AvatarPage(Page page) {
    super(page, AvatarView.class);
  }

  public WebforjLocator getProjectNameLabel() {
    return getByText("Project Alpha");
  }

  public WebforjLocator getTeamLabel() {
    return getByText("Team");
  }

  public WebforjLocator getAvatarPanel() {
    return locator(".avatar-demo__panel");
  }

  public WebforjLocator getAvatar(int index)  {
    return getByClass(Avatar.class).nth(index);
  }

  public WebforjLocator getNameLabel(int index) {
    return locator(".avatar-demo__name").nth(index);
  }

  public WebforjLocator getDialog() {
    return getByClass(Dialog.class);
  }

  public List<AvatarTheme> getThemes() {
    return List.of(
      AvatarTheme.SUCCESS,
      AvatarTheme.SUCCESS,
      AvatarTheme.WARNING,
      AvatarTheme.GRAY,
      AvatarTheme.OUTLINED_GRAY
    );
  }

  public List<String> getNames() {
    return List.of(
      "Sarah Chen", "Marcus Johnson", "Elena Rodriguez", "David Kim", "Invite Member"
    );
  }

  public WebforjLocator getRoleLabel(int index) {
    return locator(".avatar-demo__role").nth(index);
  }

  public List<String> getRoles() {
    return List.of(
      "Product Lead", "Developer", "Designer", "Developer"
    );
  }

}
