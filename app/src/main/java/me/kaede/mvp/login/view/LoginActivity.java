package me.kaede.mvp.login.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import me.kaede.mvp.R;
import me.kaede.mvp.home.view.HomeActivity;
import me.kaede.mvp.login.presenter.LoginPresenter;
import me.kaede.mvp.login.presenter.ProgressBarPresenter;


public class LoginActivity extends ActionBarActivity implements ILoginView, IProgressBarView, View.OnClickListener {

	private EditText editUser;
	private EditText editPass;
	private Button   btnLogin;
	private Button   btnClear;
	LoginPresenter loginPresenter;
	private ProgressBar progressBar;
	private ProgressBarPresenter progressBarPresenter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//find view
		editUser = (EditText) this.findViewById(R.id.et_login_username);
		editPass = (EditText) this.findViewById(R.id.et_login_password);
		btnLogin = (Button) this.findViewById(R.id.btn_login_login);
		btnClear = (Button) this.findViewById(R.id.btn_login_clear);
		progressBar = (ProgressBar) this.findViewById(R.id.progress_login);

		//set listener
		btnLogin.setOnClickListener(this);
		btnClear.setOnClickListener(this);

		//init
		loginPresenter = new LoginPresenter(this);
		progressBarPresenter = new ProgressBarPresenter(this);
		progressBarPresenter.setProgressBarVisiblity(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_login_clear:
				loginPresenter.clear();
				break;
			case R.id.btn_login_login:
				progressBarPresenter.setProgressBarVisiblity(View.VISIBLE);
				btnLogin.setEnabled(false);
				btnClear.setEnabled(false);
				loginPresenter.doLogin(editUser.getText().toString(), editPass.getText().toString());
				break;
		}
	}

	@Override
	public void clearText() {
		editUser.setText("");
		editPass.setText("");
	}

	@Override
	public void OnLoginResult(Boolean result) {
		progressBarPresenter.setProgressBarVisiblity(View.INVISIBLE);
		btnLogin.setEnabled(true);
		btnClear.setEnabled(true);
		if (result){
			Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, HomeActivity.class));
		}
		else
			Toast.makeText(this,"Login Fail",Toast.LENGTH_SHORT).show();
	}


	@Override
	public void setProgressBarVisibility(int visibility) {
		progressBar.setVisibility(visibility);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_github) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("https://github.com/kaedea/"));
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
