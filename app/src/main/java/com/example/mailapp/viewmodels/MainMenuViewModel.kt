package com.example.mailapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mailapp.R
import com.example.mailapp.model.SingleEvent

/**
 *  가로 세로가 전환되면서 bottom, rail 이 서로 바뀔때 setting 이 먼저 표시되는 경우
 *  addToBackStack 으로 인해 backStack 이 추가가 되긴하는데, 그때의 트랜잭션 상태는 mail 이 붙어있는 경우가 아니고, 그냥 빈 트랜잭션 상태이기 때문에
 *  backPressed 를 진행했을때 pop 은 되지만, pop 되었을때 트랜잭션에 붙어있는 fragment 가 없기 때문에 빈 화면이 보여진다
 *
 *  => 해결은 어떻게?
 *  1. setting fragment 를 replace 할때 backStack 이 비어있다면 default tab fragment - setting 순으로 replace?
 *      => backStack 을 통째로 보관해서 붙여넣는 방법을 찾아야 한다
 *      => 아니면, backStack 과 동일한 stack 을 따로 유지하다가, 화면 재구성시 해당 stack 대로 fragment 를 구성해줘야 한다
 *  2. pop 시 fragment 가 붙은게 없는 트랜잭션 이라면, default tab 을 show?
 *      => 언제나 pop 이후 빈 트랜잭션이라면 default fragment 를 보여준다?
 *      => 해당 방식으로 구현 성공
 *      => 현 상태에는 이렇게 해결이 가능하지만, fragment 가 3개 이상이며, 각각의 백스택을 유지해야하는 경우는 꽤 곤란할 것 같다
 */
class MainMenuViewModel: BaseViewModel() {
    private val _selectTabId: MutableLiveData<SingleEvent<Int>> = MutableLiveData()
    val selectTabId: LiveData<SingleEvent<Int>> = _selectTabId

    private val _checkTabIdx: MutableLiveData<SingleEvent<Int>> = MutableLiveData()
    val checkTabIdx: LiveData<SingleEvent<Int>> = _checkTabIdx

    private val _showMailList: MutableLiveData<SingleEvent<String>> = MutableLiveData()
    val showMailList: LiveData<SingleEvent<String>> = _showMailList

    private val _showSetting: MutableLiveData<SingleEvent<String>> = MutableLiveData()
    val showSetting: LiveData<SingleEvent<String>> = _showSetting

    /**
     * data
     */
    private var currentSelectTabId: Int = MenuItem.Mail.id
    private var backStackCount = 0
    enum class MenuItem(
        val index: Int,
        val id: Int,
        val tag: String
    ){
        Mail(0, R.id.menu_mail, "menuList"),
        Setting(1, R.id.menu_setting, "setting");
        companion object {
            operator fun get(id: Int): MenuItem? {
                values().forEach { if(it.id == id) return it }
                return null
            }
            operator fun get(tag: String?): MenuItem? {
                values().forEach { if(it.tag == tag) return it }
                return null
            }
        }
    }

    /**
     * event from view
     */
    fun setDefaultTab(){
        _selectTabId.value = SingleEvent(currentSelectTabId)
    }

    fun setSelectTab(id: Int) {
        _selectTabId.value = SingleEvent(id)
    }

    fun backStackChanged(currentSize: Int, currentTag: String?){
        Log.d("TAG", "backstack debug addOnBackStackChangedListener => stack size[$currentSize], prevCount[$backStackCount]")
        when{
            currentSize < backStackCount -> { // pop
                Log.d("TAG", "pop back stack")
                if(currentTag.isNullOrBlank()) { // 빈 트랜잭션 상태라면(setting fragment 에서 기기가 회전된다면, mail 이전에 add back stack -> replace setting 이 진행되어서 이런 경우가 발생)
                    currentSelectTabId = MenuItem.Mail.id
                    setDefaultTab()
                } else
                    setCheckTab(currentTag)
            }
        }
        backStackCount = currentSize
    }
    private fun setCheckTab(tag: String?){
        Log.d("TAG", "set check tab tag[$tag]")
        _checkTabIdx.value = SingleEvent(MenuItem[tag]?.index ?: -1)
    }

    fun clickTab(id: Int){
        Log.d("TAG", "bottomNavigationView click debug click tab at view model => id[$id]")
        currentSelectTabId = id
        when(id){
            R.id.menu_mail -> {
                _showMailList.value = SingleEvent(MenuItem[id]?.tag ?: return)
            }
            R.id.menu_setting -> {
                _showSetting.value = SingleEvent(MenuItem[id]?.tag ?: return)
            }
        }
    }
}