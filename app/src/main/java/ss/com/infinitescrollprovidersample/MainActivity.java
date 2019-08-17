package ss.com.infinitescrollprovidersample;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import ss.com.infinitescrollprovider.InfiniteScrollProvider;
import ss.com.infinitescrollprovider.OnLoadMoreListener;

public class MainActivity extends AppCompatActivity implements OnLoadMoreListener {
    private ProgressBar progressBar;
    private PostAdapter postAdapter;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        postAdapter = new PostAdapter(this);
        postAdapter.addPosts(DataFakeGenerator.getPosts(page));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(postAdapter);

        ImageView githubSourceImageView = findViewById(R.id.image_github);
        githubSourceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/saeedsh92");
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), "Choose Browser..."));
            }
        });

        InfiniteScrollProvider.Builder(1).attach(recyclerView, this);

        progressBar = findViewById(R.id.progress_bar);

    }

    @Override
    public void onLoadMore() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postAdapter.addPosts(DataFakeGenerator.getPosts(page += 1));
                progressBar.setVisibility(View.GONE);
            }
        }, 1500);
    }
}
