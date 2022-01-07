package com.geektech.a3l2retrofit.ui.fragments.post;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.a3l2retrofit.App;
import com.geektech.a3l2retrofit.R;
import com.geektech.a3l2retrofit.databinding.FragmentPostsBinding;
import com.geektech.a3l2retrofit.models.Post;
import com.geektech.a3l2retrofit.ui.interfaces.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment {

    private FragmentPostsBinding binding;
    private PostAdapter adapter;
    private NavHostFragment navHostFragment;
    private NavController controller;


    public PostFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter();
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.navHost);
        controller = navHostFragment.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

        onItemClick();
        getPosts();
        fabClick();
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Post post = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id", post);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.navHost);
                navController.navigate(R.id.formFragment, bundle);
            }

            @Override
            public void onLongClick(Post post, int position) {

                new AlertDialog.Builder(requireContext())
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                App.api.deletePost(adapter.getItem(position).getId()).enqueue(new Callback<Post>() {
                                    @Override
                                    public void onResponse(Call<Post> call, Response<Post> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            adapter.removeItem(position);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Post> call, Throwable t) {
                                        Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                                    }
                                });
                            }
                        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }

    private void getPosts() {
        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) ;
                adapter.setPosts(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    private void fabClick() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.navHost);
                navController.navigate(R.id.formFragment);
            }
        });
    }

    private void initView() {
        binding.recycler.setAdapter(adapter);
    }
}