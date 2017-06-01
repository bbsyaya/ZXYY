package com.zhixinyisheng.user.ui.IM.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.and.yzy.frame.application.Constant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.HanziToPinyin;
import com.zhixinyisheng.user.ui.IM.domain.InviteMessage;
import com.zhixinyisheng.user.ui.IM.domain.RobotUser;
import com.zhixinyisheng.user.ui.MyApplication;
import com.zhixinyisheng.user.ui.setting.CharacterParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DemoDBManager {
    static private DemoDBManager dbMgr = new DemoDBManager();
    private DbOpenHelper dbHelper;

    private DemoDBManager() {
        dbHelper = DbOpenHelper.getInstance(MyApplication.getApp());
    }

    public static synchronized DemoDBManager getInstance() {
        if (dbMgr == null) {
            dbMgr = new DemoDBManager();
        }
        return dbMgr;
    }

    /**
     * save contact list
     *
     * @param contactList
     */
    synchronized public void saveContactList(List<EaseUser> contactList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.TABLE_NAME, null, null);
            for (EaseUser user : contactList) {
                ContentValues values = new ContentValues();
                values.put(UserDao.COLUMN_NAME_ID, user.getUsername());
                if(user.getNick() != null)
                    values.put(UserDao.COLUMN_NAME_NICK, user.getNick());
                if(user.getAvatar() != null)
                    values.put(UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
                if(user.getNo() != null)
                    values.put(UserDao.COLUMN_NAME_NO, user.getNo());
                if(user.getSos() != null)
                    values.put(UserDao.COLUMN_NAME_SOS, user.getSos());
                if(user.getDatas() != null)
                    values.put(UserDao.COLUMN_NAME_DATAS, user.getDatas());
                if (user.getAgreeFlag() != -1)
                    values.put(UserDao.COLUMN_NAME_AGREEFLAG,user.getAgreeFlag());
                if (user.getIsPatient()!=-1)
                    values.put(UserDao.COLUMN_NAME_ISPATIENT,user.getIsPatient());
                if (user.getIsMyDoctor()!=-1)
                    values.put(UserDao.COLUMN_NAME_ISMYDOCTOR,user.getIsMyDoctor());
                if (user.getPayedUserID()!=-1)
                    values.put(UserDao.COLUMN_NAME_PAYEDUSERID,user.getPayedUserID());
                if (user.getCanSosSet()!=-1)
                    values.put(UserDao.COLUMN_NAME_CANSOSSET,user.getCanSosSet());
                db.replace(UserDao.TABLE_NAME, null, values);
            }
        }
    }

    /**
     * get contact list
     *
     * @return
     */
    synchronized public Map<String, EaseUser> getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, EaseUser> users = new Hashtable<String, EaseUser>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));
                String no = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NO));
                String sos = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_SOS));
                String datas = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_DATAS));
                int agreeflag = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_AGREEFLAG));
                int ispatient = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ISPATIENT));
                int ismydoctor = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ISMYDOCTOR));
                int payedUserId = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_PAYEDUSERID));
                int canSosSet = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_CANSOSSET));
                EaseUser user = new EaseUser(username);
                user.setSos(sos);
                user.setDatas(datas);
                user.setNo(no);
                user.setNick(nick);
                user.setAvatar(avatar);
                user.setAgreeFlag(agreeflag);
                user.setIsPatient(ispatient);
                user.setIsMyDoctor(ismydoctor);
                user.setPayedUserID(payedUserId);
                user.setCanSosSet(canSosSet);
                if (username.equals(Constant.NEW_FRIENDS_USERNAME) || username.equals(Constant.GROUP_USERNAME)
                        || username.equals(Constant.CHAT_ROOM)|| username.equals(Constant.CHAT_ROBOT)) {
                    user.setInitialLetter("");
                } else {
                    EaseCommonUtils.setUserInitialLetter(user);
                }
                users.put(username, user);
            }
            cursor.close();
        }
        return users;
    }

    synchronized public List<EaseUser> getContactList_list() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<EaseUser> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME /* + " desc" */, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));
                String no = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NO));
                String sos = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_SOS));
                String datas = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_DATAS));
                int agreeflag = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_AGREEFLAG));
                int ispatient = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ISPATIENT));
                int ismydoctor = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ISMYDOCTOR));
                int payedUserId = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_PAYEDUSERID));
                int canSosSet = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_CANSOSSET));
                EaseUser user = new EaseUser(username);
                user.setSos(sos);
                user.setDatas(datas);
                user.setNo(no);
                user.setNick(nick);
                user.setAvatar(avatar);
                user.setAgreeFlag(agreeflag);
                user.setIsPatient(ispatient);
                user.setIsMyDoctor(ismydoctor);
                user.setPayedUserID(payedUserId);
                user.setCanSosSet(canSosSet);
                String ch1 = ((CharacterParser.getInstance().getSelling(nick))
                        .toUpperCase()).substring(0, 1);
                user.setSortLetters(ch1);

                if (username.equals(Constant.NEW_FRIENDS_USERNAME) || username.equals(Constant.GROUP_USERNAME)
                        || username.equals(Constant.CHAT_ROOM)|| username.equals(Constant.CHAT_ROBOT)) {
                    user.setInitialLetter("");
                } else {
                    EaseCommonUtils.setUserInitialLetter(user);
                }
                list.add(user);
            }
            cursor.close();
        }
        return list;
    }



    /**
     * delete a contact
     *
     * @param username
     */
    synchronized public void deleteContact(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.TABLE_NAME, UserDao.COLUMN_NAME_ID + " = ?", new String[]{username});
        }
    }

    /**
     * save a contact
     *
     * @param user
     */
    synchronized public void saveContact(EaseUser user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.COLUMN_NAME_ID, user.getUsername());
        if(user.getNick() != null)
            values.put(UserDao.COLUMN_NAME_NICK, user.getNick());
        if(user.getAvatar() != null)
            values.put(UserDao.COLUMN_NAME_AVATAR, user.getAvatar());
        if(user.getNo() != null)
            values.put(UserDao.COLUMN_NAME_NO, user.getNo());
        if(user.getSos() != null)
            values.put(UserDao.COLUMN_NAME_SOS, user.getSos());
        if(user.getDatas() != null)
            values.put(UserDao.COLUMN_NAME_DATAS, user.getDatas());
        if (user.getAgreeFlag()!=-1)
            values.put(UserDao.COLUMN_NAME_AGREEFLAG,user.getAgreeFlag());
        if (user.getIsPatient()!=-1)
            values.put(UserDao.COLUMN_NAME_ISPATIENT,user.getIsPatient());
        if (user.getIsMyDoctor()!=-1)
            values.put(UserDao.COLUMN_NAME_ISMYDOCTOR,user.getIsMyDoctor());
        if (user.getPayedUserID()!=-1)
            values.put(UserDao.COLUMN_NAME_PAYEDUSERID,user.getPayedUserID());
        if (user.getCanSosSet()!=-1)
            values.put(UserDao.COLUMN_NAME_CANSOSSET,user.getCanSosSet());
        if(db.isOpen()){
            db.replace(UserDao.TABLE_NAME, null, values);
        }
    }

    /**
     * get a contact
     *
     * @param
     */

    synchronized public EaseUser getContact(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        EaseUser user = null;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME + " where "
                    + UserDao.COLUMN_NAME_ID + " = " +username, null);
            String userID = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
            String remarkName = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NICK));
            String avatar = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_AVATAR));
            String no = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NO));
            String sos = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_SOS));
            String datas = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_DATAS));
            int agreeflag = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_AGREEFLAG));
            int ispatient = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ISPATIENT));
            int ismydoctor = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ISMYDOCTOR));
            int payedUserId = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_PAYEDUSERID));
            int canSosSet = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_CANSOSSET));
            user = new EaseUser(username);
            user.setSos(sos);
            user.setDatas(datas);
            user.setNo(no);
            user.setNick(remarkName);
            user.setAvatar(avatar);
            user.setAgreeFlag(agreeflag);
            user.setIsPatient(ispatient);
            user.setIsMyDoctor(ismydoctor);
            user.setPayedUserID(payedUserId);
            user.setCanSosSet(canSosSet);
            if (remarkName.equals(Constant.NEW_FRIENDS_USERNAME) || remarkName.equals(Constant.GROUP_USERNAME)
                    || remarkName.equals(Constant.CHAT_ROOM) || remarkName.equals(Constant.CHAT_ROBOT)) {
                user.setInitialLetter("");
            } else {
                EaseCommonUtils.setUserInitialLetter(user);
            }
            cursor.close();
        }
        return user;
    }


    public void setDisabledGroups(List<String> groups) {
        setList(UserDao.COLUMN_NAME_DISABLED_GROUPS, groups);
    }

    public List<String> getDisabledGroups() {
        return getList(UserDao.COLUMN_NAME_DISABLED_GROUPS);
    }

    public void setDisabledIds(List<String> ids) {
        setList(UserDao.COLUMN_NAME_DISABLED_IDS, ids);
    }

    public List<String> getDisabledIds() {
        return getList(UserDao.COLUMN_NAME_DISABLED_IDS);
    }

    synchronized private void setList(String column, List<String> strList) {
        StringBuilder strBuilder = new StringBuilder();

        for (String hxid : strList) {
            strBuilder.append(hxid).append("$");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(column, strBuilder.toString());

            db.update(UserDao.PREF_TABLE_NAME, values, null, null);
        }
    }

    synchronized private List<String> getList(String column) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + column + " from " + UserDao.PREF_TABLE_NAME, null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        String strVal = cursor.getString(0);
        if (strVal == null || strVal.equals("")) {
            return null;
        }

        cursor.close();

        String[] array = strVal.split("$");

        if (array.length > 0) {
            List<String> list = new ArrayList<String>();
            Collections.addAll(list, array);
            return list;
        }

        return null;
    }

    /**
     * save a message
     *
     * @param message
     * @return return cursor of the message
     */
    public synchronized Integer saveMessage(InviteMessage message) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int id = -1;
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_ID, message.getGroupId());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUP_Name, message.getGroupName());
            values.put(InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
            values.put(InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
            values.put(InviteMessgeDao.COLUMN_NAME_STATUS, message.getStatus().ordinal());
            values.put(InviteMessgeDao.COLUMN_NAME_GROUPINVITER, message.getGroupInviter());
            db.insert(InviteMessgeDao.TABLE_NAME, null, values);

            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessgeDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }

            cursor.close();
        }
        return id;
    }

    /**
     * update message
     *
     * @param msgId
     * @param values
     */
    synchronized public void updateMessage(int msgId, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.update(InviteMessgeDao.TABLE_NAME, values, InviteMessgeDao.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(msgId)});
        }
    }

    /**
     * get messges
     *
     * @return
     */
    synchronized public List<InviteMessage> getMessagesList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + InviteMessgeDao.TABLE_NAME + " desc", null);
            while (cursor.moveToNext()) {
                InviteMessage msg = new InviteMessage();
                int id = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_ID));
                String from = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_FROM));
                String groupid = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUP_ID));
                String groupname = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUP_Name));
                String reason = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_REASON));
                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
                int status = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_STATUS));
                String groupInviter = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_GROUPINVITER));

                msg.setId(id);
                msg.setFrom(from);
                msg.setGroupId(groupid);
                msg.setGroupName(groupname);
                msg.setReason(reason);
                msg.setTime(time);
                msg.setGroupInviter(groupInviter);

                if (status == InviteMessage.InviteMesageStatus.BEINVITEED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEINVITEED);
                else if (status == InviteMessage.InviteMesageStatus.BEAGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAGREED);
                else if (status == InviteMessage.InviteMesageStatus.BEREFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEREFUSED);
                else if (status == InviteMessage.InviteMesageStatus.AGREED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.AGREED);
                else if (status == InviteMessage.InviteMesageStatus.REFUSED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.REFUSED);
                else if (status == InviteMessage.InviteMesageStatus.BEAPPLYED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.BEAPPLYED);
                else if (status == InviteMessage.InviteMesageStatus.GROUPINVITATION.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION);
                else if (status == InviteMessage.InviteMesageStatus.GROUPINVITATION_ACCEPTED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_ACCEPTED);
                else if (status == InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED.ordinal())
                    msg.setStatus(InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED);

                msgs.add(msg);
            }
            cursor.close();
        }
        return msgs;
    }

    /**
     * delete invitation message
     *
     * @param from
     */
    synchronized public void deleteMessage(String from) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(InviteMessgeDao.TABLE_NAME, InviteMessgeDao.COLUMN_NAME_FROM + " = ?", new String[]{from});
        }
    }

    synchronized int getUnreadNotifyCount() {
        int count = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select " + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessgeDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    synchronized void setUnreadNotifyCount(int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);

            db.update(InviteMessgeDao.TABLE_NAME, values, null, null);
        }
    }

    synchronized public void closeDB() {
        if (dbHelper != null) {
            dbHelper.closeDB();
        }
        dbMgr = null;
    }


    /**
     * Save Robot list
     */
    synchronized public void saveRobotList(List<RobotUser> robotList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(UserDao.ROBOT_TABLE_NAME, null, null);
            for (RobotUser item : robotList) {
                ContentValues values = new ContentValues();
                values.put(UserDao.ROBOT_COLUMN_NAME_ID, item.getUsername());
                if (item.getNick() != null)
                    values.put(UserDao.ROBOT_COLUMN_NAME_NICK, item.getNick());
                if (item.getAvatar() != null)
                    values.put(UserDao.ROBOT_COLUMN_NAME_AVATAR, item.getAvatar());
                db.replace(UserDao.ROBOT_TABLE_NAME, null, values);
            }
        }
    }

    /**
     * load robot list
     */
    synchronized public Map<String, RobotUser> getRobotList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Map<String, RobotUser> users = null;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + UserDao.ROBOT_TABLE_NAME, null);
            if (cursor.getCount() > 0) {
                users = new Hashtable<String, RobotUser>();
            }
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_ID));
                String nick = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_NICK));
                String avatar = cursor.getString(cursor.getColumnIndex(UserDao.ROBOT_COLUMN_NAME_AVATAR));
                RobotUser user = new RobotUser(username);
                user.setNick(nick);
                user.setAvatar(avatar);
                String headerName = null;
                if (!TextUtils.isEmpty(user.getNick())) {
                    headerName = user.getNick();
                } else {
                    headerName = user.getUsername();
                }
                if (Character.isDigit(headerName.charAt(0))) {
                    user.setInitialLetter("#");
                } else {
                    user.setInitialLetter(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target
                            .substring(0, 1).toUpperCase());
                    char header = user.getInitialLetter().toLowerCase().charAt(0);
                    if (header < 'a' || header > 'z') {
                        user.setInitialLetter("#");
                    }
                }

                try {
                    users.put(username, user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return users;
    }
}
