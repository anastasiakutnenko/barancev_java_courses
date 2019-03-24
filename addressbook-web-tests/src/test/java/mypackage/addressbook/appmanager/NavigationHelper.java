package mypackage.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        click(By.linkText("groups"));
    }

    public void contactCreationPage() {
        click(By.linkText("add new"));
    }

    public void homePage() {
        click(By.linkText("home"));
    }

    public void acceptBrowserAlert() {
        wd.switchTo().alert().accept();
    }
}
