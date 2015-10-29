package ua.tohateam.appinfo;

import android.app.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity implements MainCallbacks {

    private boolean mIsDualPane;
    private boolean mIsArtShowed = false;
	private SharedPreferences sharedPref;
	
	public static boolean UNINSTALL_SYSYEM = false;
	public static int SORT_BY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mIsDualPane = findViewById(R.id.item_detail_container) != null;

        //Show an art when no fragment is showed, we make sure no detail fragment is present.
        if (mIsDualPane && getFragmentManager().findFragmentByTag(DetailFragment.FRAGMENT_TAG) == null) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.icon_art);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ((FrameLayout) findViewById(R.id.item_detail_container)).addView(imageView);
            mIsArtShowed = true;
        }
    }

    @Override
    public void onItemSelected(String packageName) {
        if (mIsDualPane) {
            //Hide art when a fragment is showed.
            if (mIsArtShowed) {
                ((FrameLayout) findViewById(R.id.item_detail_container)).removeAllViews();
                mIsArtShowed = false;
            }
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.item_detail_container, DetailFragment.getInstance(packageName), DetailFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailFragment.EXTRA_PACKAGE_NAME, packageName);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            showAboutDialog();
            return true;
        }

        if (item.getItemId() == R.id.action_settings) {
            showSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
		loadOptions();
        super.onStart();
        registerReceiver(receiver, getIntentFilter());
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.about)
                .setView(getLayoutInflater().inflate(R.layout.about_dialog_message, null))
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

    /**
     * Used to update the list if a package is added or removed.
     */
    private IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addDataScheme("package");
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        filter.addAction(Intent.ACTION_PACKAGE_FULLY_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        return filter;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MainListFragment mainListFragment = (MainListFragment) getFragmentManager().findFragmentById(R.id.item_list);
            if (mainListFragment != null)
                mainListFragment.loadList();
        }
    };
	
	public void showSettings() {
		Intent intent = new Intent(this, GlobalPreferences.class);
		startActivityForResult(intent, 0);
	}

	// получаем результат
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		loadOptions();
	}

	// Загружаем настройки программы
	private void loadOptions() {
		UNINSTALL_SYSYEM = sharedPref.getBoolean("prefs_uninstall_system", false);
		SORT_BY = Integer.parseInt(sharedPref.getString("pref_sort_by", "0"));
	} // end getOptions
	
}
