package com.example.mailapp.viewmodels

import android.content.res.Configuration
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mailapp.R
import com.example.mailapp.model.MailModel
import com.example.mailapp.model.SingleEvent

class MainViewModel: BaseViewModel() {
    /**
     * event
     */
    private val _drawerMenuSelectEvent: MutableLiveData<SingleEvent<Int>> = MutableLiveData()
    val drawerMenuSelectEvent: LiveData<SingleEvent<Int>> = _drawerMenuSelectEvent

    private val _setDrawerShown: MutableLiveData<SingleEvent<Boolean>> = MutableLiveData()
    val setDrawerShown: LiveData<SingleEvent<Boolean>> = _setDrawerShown

    private val _showFragmentInfo: MutableLiveData<SingleEvent<FragmentInfo>> = MutableLiveData()
    val showFragmentInfo: LiveData<SingleEvent<FragmentInfo>> = _showFragmentInfo

    private val _mailTypeSelect: MutableLiveData<MailModel.MailType> = MutableLiveData()
    val mailTypeSelect: LiveData<MailModel.MailType> = _mailTypeSelect

    /**
     * data
     */
    enum class FragmentInfo(val tag: String){
        BottomMenu("bottom_menu"),
        RailMenu("rail_menu")
    }
    enum class MailTypeInfo(
        val mailType: MailModel.MailType,
        val tabMenuId: Int
    ){
        Primary(MailModel.MailType.Primary, R.id.menu_main_navigation_view_item_primary),
        Social(MailModel.MailType.Social, R.id.menu_main_navigation_view_item_social),
        Promotion(MailModel.MailType.Promotion, R.id.menu_main_navigation_view_item_promotions);
        companion object {
            fun get(mailType: MailModel.MailType): MailTypeInfo? {
                values().forEach {
                    if(it.mailType == mailType) return it
                }
                return null
            }
            fun get(tabMenuId: Int): MailTypeInfo? {
                values().forEach {
                    if(it.tabMenuId == tabMenuId) return it
                }
                return null
            }
        }
    }

    var selectDrawerMenuId: Int = R.id.menu_main_navigation_view_item_primary
        private set

    /**
     * event from view
     */
    fun clickOptionMenu(id: Int){
        when(id){
            android.R.id.home -> _setDrawerShown.value = SingleEvent(true)
        }
    }

    fun clickDrawerMenu(id: Int){
        Log.d("TAG", "clickDrawerMenu [$id]")
        _setDrawerShown.value = SingleEvent(false)  // drawer layout close
        selectDrawerMenuId = id
        _drawerMenuSelectEvent.value = SingleEvent(id) // drawer menu item select set
        MailTypeInfo.get(id)?.let {
            _mailTypeSelect.value = it.mailType // id 에 매칭되는 mailType 을 select
        }
    }
    fun selectMailType(mailType: MailModel.MailType){
        MailTypeInfo.get(mailType)?.let { clickDrawerMenu(it.tabMenuId) }
    }

    fun setFragmentAccordingToDevice(width: Int, orientation: Int){
        val fragmentInfo: FragmentInfo = when(orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                Log.d("TAG", "orientation changed[ORIENTATION_LANDSCAPE][$width]")
                if(width >= 600){
                    FragmentInfo.RailMenu
                }else{
                    FragmentInfo.BottomMenu
                }
            }
            // 태블릿의 경우 가로가 더 길때 세로모드일지, 가로모드일지 구분이 애매해서 일단 세로모드일때도 width 체크 코드 추가
            Configuration.ORIENTATION_PORTRAIT -> {
                Log.d("TAG", "orientation changed[ORIENTATION_PORTRAIT][$width]")
                if(width >= 600){
                    FragmentInfo.RailMenu
                }else{
                    FragmentInfo.BottomMenu
                }
            }
            Configuration.ORIENTATION_UNDEFINED -> {
                Log.d("TAG", "orientation changed[ORIENTATION_UNDEFINED]")
                FragmentInfo.BottomMenu
            }
            else -> FragmentInfo.BottomMenu
        }
        _showFragmentInfo.value = SingleEvent(fragmentInfo)
    }
}