package ar.edu.utn.frsf.isi.dam.reminder.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ar.edu.utn.frsf.isi.dam.reminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeContentFragment extends Fragment {

    private static final String TEXT = "text";

    public static HomeContentFragment newInstance(String text) {
        HomeContentFragment frag = new HomeContentFragment();

        Bundle args = new Bundle();
        args.putString(TEXT, text);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_content, container, false);

        if (getArguments() != null) {
            ((TextView) layout.findViewById(R.id.text)).setText(getArguments().getString(TEXT));
        }

        return layout;
    }}