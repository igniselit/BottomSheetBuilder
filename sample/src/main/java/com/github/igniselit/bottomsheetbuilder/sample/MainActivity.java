package com.github.igniselit.bottomsheetbuilder.sample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.github.igniselit.bottomsheetbuilder.BottomSheetBuilder;
import com.github.igniselit.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.igniselit.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.github.igniselit.bottomsheetbuilder.sample.databinding.ActivityMainBinding;
import com.github.igniselit.bottomsheetbuilder.util.BottomSheetBuilderUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity implements BottomSheetItemClickListener {

    public static final String STATE_SIMPLE = "state_simple";
    public static final String STATE_HEADER = "state_header";
    public static final String STATE_GRID = "state_grid";
    public static final String STATE_LONG = "state_long";

    private BottomSheetMenuDialog mBottomSheetDialog;
    private BottomSheetBehavior mBehavior;

    private ActivityMainBinding binding;



    private boolean mShowingSimpleDialog;
    private boolean mShowingHeaderDialog;
    private boolean mShowingGridDialog;
    private boolean mShowingLongDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        CoordinatorLayout view = binding.getRoot();
        setContentView(view);


        setSupportActionBar(binding.toolbar);

        View bottomSheet = new BottomSheetBuilder(this, binding.coordinatorLayout)
                .setMode(BottomSheetBuilder.MODE_GRID)
                .setBackgroundColorResource(android.R.color.white)
                .setMenu(R.menu.menu_bottom_grid_sheet)
                .setItemClickListener(this)
                .createView();

        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.fab.show();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClick();
            }
        });

        binding.showViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowViewClick();
            }
        });

        binding.showDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowDialogClick();
            }
        });

        binding.showDialogHeadersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowDialogHeadersClick();
            }
        });

        binding.showDialogGridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowDialogGridClick();
            }
        });

        binding.showDialogLongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShowLongDialogClick();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BottomSheetBuilderUtils.saveState(outState, mBehavior);
        outState.putBoolean(STATE_SIMPLE, mShowingSimpleDialog);
        outState.putBoolean(STATE_GRID, mShowingGridDialog);
        outState.putBoolean(STATE_HEADER, mShowingHeaderDialog);
        outState.putBoolean(STATE_LONG, mShowingLongDialog);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BottomSheetBuilderUtils.restoreState(savedInstanceState, mBehavior);
        if (savedInstanceState.getBoolean(STATE_GRID)) onShowDialogGridClick();

        if (savedInstanceState.getBoolean(STATE_HEADER)) onShowDialogHeadersClick();

        if (savedInstanceState.getBoolean(STATE_SIMPLE)) onShowDialogClick();

        if (savedInstanceState.getBoolean(STATE_LONG)) onShowLongDialogClick();
    }

    @Override
    protected void onDestroy() {
        // Avoid leaked windows
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        super.onDestroy();
    }

    public void onFabClick() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.fab.hide();
    }

    public void onShowViewClick() {
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void onShowDialogClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }

        mShowingSimpleDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setAppBarLayout(binding.appbar)
                .addTitleItem("Custom title")
                .addItem(0, "Preview", R.drawable.ic_preview_24dp)
                .addItem(1, "Share", R.drawable.ic_share_24dp)
                .addDividerItem()
                .addItem(2, "Get link", R.drawable.ic_link_24dp)
                .addItem(3, "Make a copy", R.drawable.ic_content_copy_24dp)
                .expandOnStart(true)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingSimpleDialog = false;
                    }
                })
                .createDialog();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingSimpleDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }

    public void onShowDialogHeadersClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingHeaderDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setAppBarLayout(binding.appbar)
                .setMenu(R.menu.menu_bottom_headers_sheet)
                .expandOnStart(true)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingHeaderDialog = false;
                    }
                })
                .createDialog();
        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingHeaderDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }

    public void onShowDialogGridClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingGridDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_GRID)
                .setAppBarLayout(binding.appbar)
                .setMenu(getResources().getBoolean(R.bool.tablet_landscape)
                        ? R.menu.menu_bottom_grid_tablet_sheet : R.menu.menu_bottom_grid_sheet)
                .expandOnStart(true)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingGridDialog = false;
                    }
                })
                .createDialog();

        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingGridDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }

    public void onShowLongDialogClick() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
        mShowingLongDialog = true;
        mBottomSheetDialog = new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog_Custom)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setAppBarLayout(binding.appbar)
                .setMenu(R.menu.menu_bottom_list_sheet)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        Log.d("Item click", item.getTitle() + "");
                        mShowingLongDialog = false;
                    }
                })
                .createDialog();

        mBottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mShowingLongDialog = false;
            }
        });
        mBottomSheetDialog.show();
    }

    @Override
    public void onBottomSheetItemClick(MenuItem item) {
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}