package com.teamzenith.game.zpuzzle.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.SendInvitation;

import java.util.HashMap;

/**
 * Created by alaaalkassar on 4/9/17.
 */

public class InboxInvitationsAdapter extends BaseAdapter {
    private HashMap<Integer, SendInvitation> userInvitation = new HashMap<>();
    private Activity inboxActivity;
    private static LayoutInflater inflater = null;

    public InboxInvitationsAdapter(Activity inboxActivity, HashMap<Integer, SendInvitation> userInvitation) {
        this.inboxActivity = inboxActivity;
        this.userInvitation = userInvitation;
        inflater = (LayoutInflater) inboxActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return userInvitation.size();
    }

    @Override
    public Object getItem(int position) {
        return userInvitation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Context context = parent.getContext();
        if (convertView == null)
            view = inflater.inflate(R.layout.inbox_items, null);

        TextView invitationLetter = (TextView) view.findViewById(R.id.inbox_invitation_letter);
        TextView level = (TextView) view.findViewById(R.id.inbox_invitation_level);
        ImageView thumb_image = (ImageView) view.findViewById(R.id.inbox_invitation_image);
        TextView invitationStatus = (TextView) view.findViewById(R.id.inbox_invitation_status);

        boolean status = this.userInvitation.get(position).isStatus();

        invitationLetter.setText(this.userInvitation.get(position).getIntiationText());

        level.setText(this.userInvitation.get(position).getLevel());

        Picasso.with(context).load(userInvitation.get(position).getImageURL()).into(thumb_image);

        invitationStatus.setText(String.valueOf(status));

        return view;
    }
}

