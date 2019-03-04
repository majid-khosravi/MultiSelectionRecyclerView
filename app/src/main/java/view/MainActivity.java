package view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import controller.OnItemClickListener;
import controller.OnRecyclerItemClickListener;
import ir.khosravi.multiselection.R;
import model.CityModel;

public class MainActivity extends AppCompatActivity {

    private MultiSelectRecyclerView mainRecyclerView;
    private ImageView btnCancel;
    private TextView txtItemCount;
    private CheckBox chkAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        dataLoad();
    }

    public void dataLoad() {
        List<CityModel> cityModels = new ArrayList<>();

        String city[] = {"Tokyo", "Delhi", "Shanghai", "Sao Paulo", "Mumbai", "Mexico City", "Beijing", "Osaka", "Cairo", "New York"};
        String country[] = {"Japan", "India", "China", "Brazil", "India", "Mexico", "China", "Japan", "Egypt", "United States"};

        for (int i = 0; i < city.length; i++) {
            CityModel mSample = new CityModel(city[i], country[i]);
            cityModels.add(mSample);
        }

        mainRecyclerView.setRecyclerList(cityModels, btnCancel, txtItemCount, chkAll);

        mainRecyclerView.setOnRecyclerItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object object) {
                CityModel model = (CityModel) object;
                Toast.makeText(MainActivity.this, "Item " + position + "(" + model.getName() + ") Clicked.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mainRecyclerView = findViewById(R.id.main_recyclerView);
        btnCancel = findViewById(R.id.multiSelectRecycler_toolbar_btnCancel);
        txtItemCount = findViewById(R.id.multiSelectRecycler_toolbar_txtItemCount);
        chkAll = findViewById(R.id.multiSelectRecycler_toolbar_chkAll);
    }
}
