package mobileprogramming.devinwinardi.clevernote.ui.task;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import id.ac.ui.cs.mobileprogramming.devinwinardi.clevernote.R;
import mobileprogramming.devinwinardi.clevernote.OpenGLActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class PermissionFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private View root;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.req_permission, container, false);

        Button check_permission = (Button) root.findViewById(R.id.check_permission);
        Button request_permission = (Button) root.findViewById(R.id.request_permission);
        check_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = v;

                int id = v.getId();
                switch (id) {
                    case R.id.check_permission:
                        if (checkPermission()) {

                            Snackbar.make(root, "Permission already granted.", Snackbar.LENGTH_LONG).show();

                        } else {

                            Snackbar.make(root, "Please request permission.", Snackbar.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.request_permission:
                        if (!checkPermission()) {

                            requestPermission();

                        } else {

                            Snackbar.make(root, "Permission already granted.", Snackbar.LENGTH_LONG).show();

                        }
                        break;
                }
            }
        });
        request_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root = v;

                int id = v.getId();
                switch (id) {
                    case R.id.check_permission:
                        if (checkPermission()) {

                            Snackbar.make(root, "Permission already granted.", Snackbar.LENGTH_LONG).show();

                        } else {

                            Snackbar.make(root, "Please request permission.", Snackbar.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.request_permission:
                        if (!checkPermission()) {

                            requestPermission();

                        } else {

                            Snackbar.make(root, "Permission already granted.", Snackbar.LENGTH_LONG).show();

                        }
                        break;
                }
            }
        });
        Button check_network = (Button) root.findViewById(R.id.check_network);
        check_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) PermissionFragment.this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
                    Snackbar.make(root, "Connected to network", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Snackbar.make(root, "Not connected to network", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Button show_credit =  (Button) root.findViewById(R.id.show_credit);
        show_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OpenGLActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        this.requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                        Snackbar.make(root, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("Access is needed to make sure application went smoothly. Please allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
