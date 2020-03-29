package upv.cuniculappteam.cuniculapp.activity.main;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import upv.cuniculappteam.cuniculapp.R;
import upv.cuniculappteam.cuniculapp.activity.calendar.CalendarFragment;
import upv.cuniculappteam.cuniculapp.activity.cycles.CyclesFragment;
import upv.cuniculappteam.cuniculapp.activity.labor.LaborFragment;
import upv.cuniculappteam.cuniculapp.activity.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener
{
    private static final Map<Integer, Fragment> fragments = new HashMap<>();
    static {
        fragments.put(R.id.main_calendar, new CalendarFragment());
        fragments.put(R.id.main_cycles, new CyclesFragment());
        fragments.put(R.id.main_labor, new LaborFragment());
        fragments.put(R.id.main_settings, new SettingsFragment());
    }

    private static final @IdRes int mainFragment = R.id.main_cycles;

    private Fragment activeFragment;

    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFragment();

        this.menu = findViewById(R.id.main_menu);
        this.menu.setOnNavigationItemSelectedListener(this);
        this.menu.setSelectedItemId(mainFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Fragment fragment;
        if ((fragment = fragments.get(item.getItemId())) != null)
            switchFragment(fragment);

        return true;
    }

    private void switchFragment(@NonNull Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (activeFragment != null)
            transaction.hide(activeFragment);
        transaction.show(fragment);
        transaction.commit();

        activeFragment = fragment;
    }

    private void initializeFragment()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;

        for (Integer id : fragments.keySet())
        {
            if ((fragment = fragments.get(id)) != null)
                transaction.add(R.id.main_fragment, fragment).hide(fragment);
        }

        transaction.commit();
    }
}
