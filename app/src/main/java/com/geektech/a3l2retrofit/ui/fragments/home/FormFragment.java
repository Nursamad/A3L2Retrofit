package com.geektech.a3l2retrofit.ui.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.a3l2retrofit.App;
import com.geektech.a3l2retrofit.R;
import com.geektech.a3l2retrofit.databinding.FragmentFormBinding;
import com.geektech.a3l2retrofit.models.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormFragment extends Fragment {
    private Post post;
    private FragmentFormBinding binding;
    private static final int USER_ID = 0;
    private static final int GROUP_ID = 36;
    private boolean isChanged = false;


    private NavHostFragment navHostFragment;
    private NavController controller;

    public FormFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.navHost);
        controller = navHostFragment.getNavController();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            post = (Post) requireArguments().getSerializable("id");
            isChanged = true;
            setPosts();
        }
        sendBtn();
    }

    private void sendBtn() {
        binding.sendBtn.setOnClickListener(v -> {
            if (!isChanged) {
                buttonClick();
            } else {
                updatePost();
            }
        });
    }

    private void updatePost() {
        String title = binding.titleEdt.getText().toString();
        String content = binding.contentEdt.getText().toString();
        post.setTitle(title);
        post.setContent(content);
//        Post post2 = new Post(title , content , USER_ID , GROUP_ID);
        App.api.updatePost(post.getId(), post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(requireActivity(), "sssss", Toast.LENGTH_SHORT).show();
                    controller.navigateUp();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }


    private void setPosts() {
        binding.titleEdt.setText(post.getTitle());
        binding.contentEdt.setText(post.getContent());
    }

    private void buttonClick() {
        post = new Post(
                binding.titleEdt.getText().toString(),
                binding.contentEdt.getText().toString(),
                USER_ID,
                GROUP_ID
        );
        App.api.createPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    controller.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }
}