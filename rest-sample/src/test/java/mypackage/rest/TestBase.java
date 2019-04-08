package mypackage.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;

public class TestBase {
    private Executor getExecutor() {
        return Executor.newInstance().
                auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    public String getIssueStatus(int issueId) throws IOException {
        String status = "";
        String json = getExecutor()
                .execute(Request.Get("http://bugify.stqa.ru/api/issues/" + issueId + ".json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonArray issues = (JsonArray) parsed.getAsJsonObject().get("issues");
        for(JsonElement issue : issues) {
            status = issue.getAsJsonObject().get("state_name").getAsString();
        }
        return status;
    }
    public boolean isIssueOpen(int issueId) throws IOException {
        String status = getIssueStatus(issueId);
        if (status.equals("Fixed")) {
            return false;
        }
        return true;
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}