package com.majeur.applicationsinfo;

import android.app.*;
import android.content.*;
import android.net.*;
import android.view.*;
import android.widget.*;
import com.majeur.applicationsinfo.utils.*;

public class DetailOverflowMenu implements View.OnClickListener, PopupMenu.OnMenuItemClickListener
 {

    private Context mContext;
    private String mPackageName;
	private Activity mActivity;
	
    public DetailOverflowMenu(Context context, String packageName, Activity a) {
        mContext = context;
        mPackageName = packageName;
		mActivity = a;
    }

    public void setView(View view) {
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.inflate(R.menu.fragment_detail);

        // Disable/Enable item menu if root
		// !mSettings.UNINSTALL_SYSYEM & 
		if (MyUtils.isSystemApp(mContext, mPackageName)) {
            popupMenu.getMenu().findItem(R.id.action_uninstall).setEnabled(false);
        } else {
            popupMenu.getMenu().findItem(R.id.action_uninstall).setEnabled(true);
        }

		// Show/Hide item menu
        if (ShellInterface.isSuAvailable()) {
			popupMenu.getMenu().findItem(R.id.action_freezen).setVisible(MyUtils.isFrozenApp(mContext, mPackageName));
        	popupMenu.getMenu().findItem(R.id.action_unfreezen).setVisible(!MyUtils.isFrozenApp(mContext, mPackageName));
		} else {
			popupMenu.getMenu().findItem(R.id.action_freezen).setVisible(MyUtils.isFrozenApp(mContext, mPackageName));
        	popupMenu.getMenu().findItem(R.id.action_unfreezen).setVisible(!MyUtils.isFrozenApp(mContext, mPackageName));
			popupMenu.getMenu().findItem(R.id.action_freezen).setEnabled(false);
			popupMenu.getMenu().findItem(R.id.action_unfreezen).setEnabled(false);			
		}
		
//        popupMenu.getMenu().findItem(R.id.action_uninstall).setEnabled(!MyUtils.isSystemApp(mContext, mPackageName));

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_uninstall:
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE);
                uninstallIntent.setData(Uri.parse("package:" + mPackageName));
                mContext.startActivity(uninstallIntent);
                return true;
            case R.id.action_view_in_settings:
                Intent infoIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                infoIntent.addCategory(Intent.CATEGORY_DEFAULT);
                infoIntent.setData(Uri.parse("package:" + mPackageName));
                mContext.startActivity(infoIntent);
                return true;
            case R.id.action_view_manifest:
                Intent viewManifestIntent = new Intent(mContext, ViewManifestActivity.class);
                viewManifestIntent.putExtra(ViewManifestActivity.EXTRA_PACKAGE_NAME, mPackageName);
                mContext.startActivity(viewManifestIntent);
                return true;
			case R.id.action_kill:
				killApp();
                return true;
		}
        return false;
    }

	// Kill app dialig
	private void killApp() {
		new AlertDialog.Builder(mActivity)
			.setIcon(R.drawable.ic_alert)
			.setTitle("Kill application?")
			.setMessage("Are you sure?\n Stop the application?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MyUtils.killApp(mPackageName, mActivity);
                }
			})
			.setNegativeButton("No", null)
			.show();
	}
//    public boolean isSystemApp() {
//        try {
//            return (mContext.getPackageManager().getApplicationInfo(mPackageName, 0).flags & ApplicationInfo.FLAG_SYSTEM) != 0;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
