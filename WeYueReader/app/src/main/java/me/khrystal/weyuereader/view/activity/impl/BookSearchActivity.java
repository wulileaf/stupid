package me.khrystal.weyuereader.view.activity.impl;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.khrystal.weyuereader.R;
import me.khrystal.weyuereader.model.BookBean;
import me.khrystal.weyuereader.view.adapter.BookInfoAdapter;
import me.khrystal.weyuereader.view.base.BaseActivity;
import me.khrystal.weyuereader.view.fragment.IBookSearchInfo;
import me.khrystal.weyuereader.viewmodel.activity.VMBookSearchInfo;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 18/5/7
 * update time:
 * email: 723526676@qq.com
 */

public class BookSearchActivity extends BaseActivity implements IBookSearchInfo {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
    @BindView(R.id.iv_clear)
    ImageView mIvClear;
    @BindView(R.id.et_search)
    EditText mEtSearch;

    private VMBookSearchInfo mModel;
    List<BookBean> mBeans = new ArrayList<>();
    private BookInfoAdapter mBookInfoAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mModel = new VMBookSearchInfo(mContext, this);
        setBinddingView(R.layout.activity_book_search, NO_BINDDING, mModel);
    }

    @Override
    protected void initView() {
        super.initView();
        mBookInfoAdapter = new BookInfoAdapter(mBeans);
        mRvSearch.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSearch.setAdapter(mBookInfoAdapter);

        mBookInfoAdapter.setOnItemClickListener(((adapter, view, position) -> {
            Intent intent = new Intent();
            intent.setClass(mContext, BookDetailActivity.class);
            intent.putExtra("bookid", mBeans.get(position).get_id());
            if (android.os.Build.VERSION.SDK_INT > 20) {
                ImageView imageView = view.findViewById(R.id.book_brief_iv_portrait);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, imageView, "bookImage").toBundle());
            } else {
                startActivity(intent);
            }
        }));

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mModel.searchBooks(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void getSearchBooks(List<BookBean> bookBeans) {
        mBeans.clear();
        mBeans.addAll(bookBeans);
        mBookInfoAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clear:
                mEtSearch.setText("");
                break;
        }
    }
}
