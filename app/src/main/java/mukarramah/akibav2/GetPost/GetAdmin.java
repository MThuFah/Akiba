package mukarramah.akibav2.GetPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAdmin {
    @SerializedName("result")
    @Expose
    private List<Admin> result = null;

    public List<Admin> getResult() {
        return result;
    }

    public void setResult(List<Admin> result) {
        this.result = result;
    }
}

