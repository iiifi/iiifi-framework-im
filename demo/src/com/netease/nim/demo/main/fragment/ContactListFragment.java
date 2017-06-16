package com.netease.nim.demo.main.fragment;

import android.content.Context;
import android.os.Bundle;

import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;
import com.netease.nim.demo.main.activity.SystemMessageActivity;
import com.netease.nim.demo.main.activity.TeamListActivity;
import com.netease.nim.demo.main.model.MainTab;
import com.netease.nim.demo.session.SessionHelper;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.contact.ContactsFragment;
import com.netease.nim.uikit.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.contact.core.item.ItemTypes;


/**
 * 集成通讯录列表
 * <p/>
 * Created by huangjun on 2015/9/7.
 */
public class ContactListFragment extends MainTabFragment {

    private ContactsFragment fragment;

    public ContactListFragment() {
        setContainerId(MainTab.CONTACT.fragmentId);
    }

    /**
     * ******************************** 功能项定制 ***********************************
     */
    public final static class FuncItem extends AbsContactItem {
        static final FuncItem VERIFY = new FuncItem();
        static final FuncItem NORMAL_TEAM = new FuncItem();
        static final FuncItem ADVANCED_TEAM = new FuncItem();
        static final FuncItem BLACK_LIST = new FuncItem();
        static final FuncItem MY_COMPUTER = new FuncItem();

        @Override
        public int getItemType() {
            return ItemTypes.FUNC;
        }

        @Override
        public String belongsGroup() {
            return null;
        }

        static void handle(Context context, AbsContactItem item) {
            if (item == VERIFY) {
                SystemMessageActivity.start(context);
            } else if (item == NORMAL_TEAM) {
                TeamListActivity.start(context, ItemTypes.TEAMS.NORMAL_TEAM);
            } else if (item == ADVANCED_TEAM) {
                TeamListActivity.start(context, ItemTypes.TEAMS.ADVANCED_TEAM);
            } else if (item == MY_COMPUTER) {
                SessionHelper.startP2PSession(context, DemoCache.getAccount());
            }
        }
    }


    /**
     * ******************************** 生命周期 ***********************************
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onCurrent(); // 触发onInit，提前加载
    }

    @Override
    protected void onInit() {
        addContactFragment();  // 集成通讯录页面
    }

    // 将通讯录列表fragment动态集成进来。 开发者也可以使用在xml中配置的方式静态集成。
    private void addContactFragment() {
        fragment = new ContactsFragment();
        fragment.setContainerId(R.id.contact_fragment);

        UI activity = (UI) getActivity();

        // 如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
        fragment = (ContactsFragment) activity.addFragment(fragment);
    }

    @Override
    public void onCurrentTabClicked() {
        // 点击切换到当前TAB
        if (fragment != null) {
            fragment.scrollToTop();
        }
    }
}
