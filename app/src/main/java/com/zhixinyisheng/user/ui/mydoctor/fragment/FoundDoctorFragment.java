package com.zhixinyisheng.user.ui.mydoctor.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayout;
import com.and.yzy.frame.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.and.yzy.frame.util.RetrofitUtils;
import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.adapter.doctor.CityAdapter;
import com.zhixinyisheng.user.adapter.doctor.FoundDoctorAdapter;
import com.zhixinyisheng.user.adapter.doctor.JobNamedAdapter;
import com.zhixinyisheng.user.adapter.doctor.ProvinceAdapter;
import com.zhixinyisheng.user.adapter.doctor.RecommedDoctorAdapter;
import com.zhixinyisheng.user.adapter.doctor.SectionAdapter;
import com.zhixinyisheng.user.config.HttpIdentifier;
import com.zhixinyisheng.user.config.UserManager;
import com.zhixinyisheng.user.domain.doctor.AreaConstant;
import com.zhixinyisheng.user.domain.doctor.Doctor;
import com.zhixinyisheng.user.domain.doctor.JsonResponse;
import com.zhixinyisheng.user.domain.doctor.SectionChoice;
import com.zhixinyisheng.user.http.DoctorRequestBody;
import com.zhixinyisheng.user.ui.BaseFgt;
import com.zhixinyisheng.user.ui.mydoctor.activity.DoctorPageActivity;
import com.zhixinyisheng.user.ui.mydoctor.activity.MyDoctorPageActivity;
import com.zhixinyisheng.user.view.ClearEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Yuanyx on 2016/12/30.
 */

public class FoundDoctorFragment extends BaseFgt implements CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemClickListener, SwipyRefreshLayout.OnRefreshListener, TextView.OnEditorActionListener {
    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.cb_position)
    CheckBox cbPosition;
    @Bind(R.id.cb_department)
    CheckBox cbDepartment;
    @Bind(R.id.cb_profession)
    CheckBox cbProfession;
    @Bind(R.id.lv_doctor)
    ListView lvDoctor;
    @Bind(R.id.lv_province)
    ListView lvProvince;
    @Bind(R.id.lv_city)
    ListView lvCity;
    @Bind(R.id.lv_job)
    ListView lvJob;
    @Bind(R.id.lv_section)
    ListView lvSection;
    @Bind(R.id.lv_section_junior)
    ListView lvSectionJunior;
    @Bind(R.id.popup_area)
    LinearLayout llArea;
    @Bind(R.id.popup_section)
    LinearLayout llSection;
    @Bind(R.id.popup_job)
    LinearLayout llJob;
    @Bind(R.id.srl)
    SwipyRefreshLayout mSrl;
    @Bind(R.id.lv_no_result)
    ListView lvNoResult;
    private FoundDoctorAdapter mFoundDoctorAdapter;
    private RecommedDoctorAdapter recommedDoctorAdapter;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdpater;
    private SectionAdapter mSectionAdapter;
    private SectionAdapter mSectionJuniorAdapter;//小科室的适配器
    private JobNamedAdapter mJobNamedAdapter;

    private List<String> mProvinces;
    private List<String> mCitys;
    private List<String> mJobs;
    //    private List<Doctor.ListBean> mDoctors;
    private List<SectionChoice.ListBean> mSections;

    List<SectionChoice.ListBean> list_major = new ArrayList<>();
    List<SectionChoice.ListBean> list_juior = new ArrayList<>();

    private String[][] mCityArray;
    //科室 职称 用户所在省份 用户所在城市
    private String mSection, mJob, mLocalProvince, mLocalCity;
    private int mPage = 1;
    private String province, city, keyWord;
    private List<Doctor.ListBean> doctorListRe;
    String sectionId;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found_doctor;
    }

    /**
     * 初始化本地数据
     */
    private void initLocationData() {
        userId = UserManager.getUserInfo().getUserId();
        String[] provinceArray = AreaConstant.PROVINCE;
        mProvinces = Arrays.asList(provinceArray);
        mProvinceAdapter.setData(mProvinces);
        mCityArray = AreaConstant.CITY;
        mCitys = Arrays.asList(mCityArray[0][0]);
        mCityAdpater.setData(mCitys);

        String[] job = AreaConstant.JOB_NAMED;
        mJobs = Arrays.asList(job);
        mJobNamedAdapter.setData(mJobs);

        mLocalCity = UserManager.getUserInfo().getCity();
        mLocalProvince = UserManager.getUserInfo().getProvince();
        if (mLocalCity.equals("")){
            mLocalCity = "石家庄";
        }
        cbPosition.setText(mLocalCity);
        cbProfession.setText("全部职称");
    }

    private void initView() {
        cbProfession.setOnCheckedChangeListener(this);
        cbDepartment.setOnCheckedChangeListener(this);
        cbPosition.setOnCheckedChangeListener(this);
        lvDoctor.setOnItemClickListener(this);
        lvCity.setOnItemClickListener(this);
        lvProvince.setOnItemClickListener(this);
        lvJob.setOnItemClickListener(this);
        lvSection.setOnItemClickListener(this);
        lvSectionJunior.setOnItemClickListener(this);
        lvNoResult.setOnItemClickListener(this);
        mSrl.setOnRefreshListener(this);
        mSrl.setColorSchemeColors(Color.parseColor("#25CDBC"));
        etSearch.setOnEditorActionListener(this);
    }

    private void initAdapter() {
        mFoundDoctorAdapter = new FoundDoctorAdapter(getActivity());
        lvDoctor.setAdapter(mFoundDoctorAdapter);

        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.view_reommed_doctor_head, null, false);
        recommedDoctorAdapter = new RecommedDoctorAdapter(getActivity());
        lvNoResult.addHeaderView(headView);
        lvNoResult.setAdapter(recommedDoctorAdapter);


        mProvinceAdapter = new ProvinceAdapter(getActivity());
        lvProvince.setAdapter(mProvinceAdapter);

        mCityAdpater = new CityAdapter(getActivity());
        lvCity.setAdapter(mCityAdpater);

        mSectionAdapter = new SectionAdapter(getActivity());
        lvSection.setAdapter(mSectionAdapter);

        mSectionJuniorAdapter = new SectionAdapter(getActivity());
        lvSectionJunior.setAdapter(mSectionJuniorAdapter);

        mJobNamedAdapter = new JobNamedAdapter(getActivity());
        lvJob.setAdapter(mJobNamedAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_position:
                if (b) {
                    llArea.setVisibility(View.VISIBLE);
                    cbDepartment.setChecked(false);
                    cbProfession.setChecked(false);
                } else {
                    llArea.setVisibility(View.GONE);
                }
                break;
            case R.id.cb_department:
                if (b) {
                    llSection.setVisibility(View.VISIBLE);
                    cbPosition.setChecked(false);
                    cbProfession.setChecked(false);
                } else {
                    llSection.setVisibility(View.GONE);
                }

                break;
            case R.id.cb_profession:
                if (b) {
                    llJob.setVisibility(View.VISIBLE);
                    cbPosition.setChecked(false);
                    cbDepartment.setChecked(false);
                } else {
                    llJob.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String doctorId = "";
        Intent intent;
        switch (adapterView.getId()) {
            case R.id.lv_no_result:
                if (0 == i) {
                    return;
                }
                Doctor.ListBean doctorRe = doctorListRe.get(i - 1);
                //医生id
                doctorId = doctorRe.getId();
                if (doctorId.equals(userId)) {
                    intent = new Intent(getActivity(), MyDoctorPageActivity.class);
                } else {
                    intent = new Intent(getActivity(), DoctorPageActivity.class);
                    intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, doctorId);
                }
                startActivity(intent);
                activityAnimation();
                break;
            case R.id.lv_doctor:
                Doctor.ListBean doctor = mFoundDoctorAdapter.getItem(i);
                //医生id
                doctorId = doctor.getId();
                if (doctorId.equals(userId)) {
                    intent = new Intent(getActivity(), MyDoctorPageActivity.class);
                } else {
                    intent = new Intent(getActivity(), DoctorPageActivity.class);
                    intent.putExtra(DoctorPageActivity.EXTRA_DOCTOR_ID, doctorId);
                }
                startActivity(intent);
                activityAnimation();
                break;
            case R.id.lv_province:
                mProvinceAdapter.setChoosePos(i);
                //级联
                mCitys = Arrays.asList(mCityArray[i]);
                mCityAdpater.setData(mCitys);
                if (0 == i) {
                    province = "all";
                }
                break;
            case R.id.lv_city:
                if (i == 0) {
                    city = "all";
                    if ("all".equals(province)) {
                        cbPosition.setText("全部位置");
                    } else {
                        province = mProvinceAdapter.getItem(mProvinceAdapter.getChoosePos());
                        cbPosition.setText(province);
                    }
                    cbPosition.setChecked(false);
                    loadSearchDoctor(true);
                    return;
                }
                mCityAdpater.setChoosePos(i);
                cbPosition.setChecked(false);
                //查询条件
                int selectProvincePos = mProvinceAdapter.getChoosePos();
                province = mProvinceAdapter.getItem(selectProvincePos);

                int selectCityPos = mCityAdpater.getChoosePos();
                city = mCityAdpater.getItem(selectCityPos);
                cbPosition.setText(city);

                loadSearchDoctor(true);
                break;
            case R.id.lv_section:
                list_juior.clear();
                SectionChoice.ListBean sl = new SectionChoice.ListBean();
                sl.setName("全部科室");
                list_juior.add(sl);
                for (int j = 0; j < mSections.size(); j++) {
                    if (mSections.get(j).getIsRoot() == 0) {
                        if (mSections.get(j).getLastId().equals(list_major.get(i).getSectionId())) {
                            list_juior.add(mSections.get(j));
                        }
                    }
                }
                if (0 == i) {
                    mSection = "all";
                }
                mSectionAdapter.setChoosePos(i);
                mSectionJuniorAdapter.setChoosePos(0);
                break;
            case R.id.lv_section_junior:
                if ("all".equals(mSection) && 0 == i) {
                    mSection = "all";
                    cbDepartment.setText("全部科室");
                } else if (!"all".equals(mSection) && 0 == i) {
                    //保持大课室的值
                    mSection = mSectionAdapter.getItem(mSectionAdapter.getChoosePos()).getName();
                    cbDepartment.setText(mSection);
                } else {
                    mSection = list_juior.get(i).getName();
                    cbDepartment.setText(mSection);
                }
                sectionId = list_juior.get(i).getSectionId();
                mSectionJuniorAdapter.setChoosePos(i);
                cbDepartment.setChecked(false);
                loadSearchDoctor(true);
                break;
            case R.id.lv_job:
                if (i == 0) {
                    mJob = "all";
                    cbProfession.setChecked(false);
                    cbProfession.setText("全部职称");
                    loadSearchDoctor(true);
                    return;
                }
                mJobNamedAdapter.setChoosePos(i);
                cbProfession.setChecked(false);
                mJob = mJobNamedAdapter.getItem(i);
                cbProfession.setText(mJobNamedAdapter.getItem(i));
                loadSearchDoctor(true);
                break;
        }
    }

    @Override
    public void initData() {
        initAdapter();
        initLocationData();
        initView();
        loadSectionData();
        loadSearchDoctor(true);
        recommendDoctor();
    }

    /**
     * 加载科室数据
     */
    private void loadSectionData() {
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).sectionList(phone, secret), HttpIdentifier.SECTION);
    }

    /**
     * 加载查询医生数据
     */
    private void loadSearchDoctor(boolean isShowDialog) {
        if (isShowDialog) {
            showLoadingDialog(null);
        }
        keyWord = etSearch.getText().toString().trim();
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).searchDoctor(phone, secret, userId,
                HttpIdentifier.PAGECOUNT, String.valueOf(mPage), keyWord, province, city, mSection, mJob, mLocalProvince, mLocalCity),
                HttpIdentifier.SEARCH_DOCTOR);
    }

    /**
     * 推荐医生
     */
    private void recommendDoctor() {
        doHttp(RetrofitUtils.createApi(DoctorRequestBody.class).recommendDoctor(phone, secret, userId,
                "3", "1", UserManager.getUserInfo().getProvince(), UserManager.getUserInfo().getCity(), null),
                HttpIdentifier.RECOMMEND_DOCTOR);
    }

    @Override
    public void onSuccess(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        super.onSuccess(result, call, response, what);
        switch (what) {
            case HttpIdentifier.SECTION://科室
                SectionChoice section = JSON.parseObject(result, SectionChoice.class);
                mSections = section.getList();

                for (int i = 0; i < mSections.size(); i++) {
                    if (mSections.get(i).getIsRoot() == 1) {
                        list_major.add(mSections.get(i));
                    }
                }

                if (!"全部科室".equals(list_major.get(0).getName())) {
                    SectionChoice.ListBean bean = new SectionChoice.ListBean();
                    bean.setName("全部科室");
                    list_major.add(0, bean);
                }

                mSectionAdapter.setData(list_major);
                mSectionAdapter.setChoosePos(0);

                SectionChoice.ListBean sl = new SectionChoice.ListBean();
                sl.setName("全部科室");
                list_juior.add(sl);
                for (int i = 0; i < mSections.size(); i++) {
                    if (mSections.get(i).getIsRoot() == 0) {
                        if (mSections.get(i).getLastId().equals(list_major.get(0).getSectionId())) {

                            list_juior.add(mSections.get(i));
                        }
                    }
                }
                mSectionJuniorAdapter.setData(list_juior);

                mSectionJuniorAdapter.setChoosePos(0);
                cbDepartment.setText("全部科室");

                break;
            case HttpIdentifier.RECOMMEND_DOCTOR://推荐医生
                Doctor doctorRe = JSON.parseObject(result, Doctor.class);
                doctorListRe = doctorRe.getList();
                recommedDoctorAdapter.setData(doctorListRe);
                break;
            case HttpIdentifier.SEARCH_DOCTOR://查询医生
                mSrl.setRefreshing(false);
                Doctor doctor = JSON.parseObject(result, Doctor.class);
                List<Doctor.ListBean> doctorList = doctor.getList();
                if (mPage == 1) {
                    lvDoctor.setVisibility(View.VISIBLE);
                    lvNoResult.setVisibility(View.GONE);
                    if (doctorList.size() == 0) {
                        lvDoctor.setVisibility(View.GONE);
                        lvNoResult.setVisibility(View.VISIBLE);
                        return;
                    }
                    mFoundDoctorAdapter.setData(doctorList);
                } else if (doctorList != null && doctorList.size() != 0) {
                    mFoundDoctorAdapter.addData(doctorList);
                } else {
                    mPage--;
                    showToast("暂无数据");
                }
                break;
        }
    }

    /**
     * @param result
     * @param call
     * @param response
     * @param what
     */
    @Override
    public void onFailure(String result, Call<ResponseBody> call, Response<ResponseBody> response, int what) {
        try {
            JsonResponse response1 = JSON.parseObject(result, JsonResponse.class);
            showToast(response1.getRetMessage());
        } catch (Exception e) {

        }
    }

    @Override
    public void onError(Call<ResponseBody> call, Throwable t, int what) {
        super.onError(call, t, what);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            mPage = 1;
            loadSearchDoctor(false);
        } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
            mPage++;
            loadSearchDoctor(false);
        }
    }

    /**
     * 监听软键盘搜索键
     *
     * @param textView
     * @param actionId
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

            String keyWord = etSearch.getText().toString().trim();

            if (TextUtils.isEmpty(keyWord)) {
                showToast("请输入搜索关键字");
                return true;
            }
            llArea.setVisibility(View.GONE);
            llJob.setVisibility(View.GONE);
            llSection.setVisibility(View.GONE);

            cbDepartment.setChecked(false);
            cbProfession.setChecked(false);
            cbPosition.setChecked(false);
            // 搜索功能主体
            loadSearchDoctor(true);
            return true;
        }
        return false;
    }
}
