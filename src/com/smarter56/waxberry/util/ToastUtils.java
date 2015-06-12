package com.smarter56.waxberry.util;

import com.smarter56.waxberry.R;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Utilities for displaying toast notifications
 */
public class ToastUtils {

	/**
	 * Show the given message in a {@link Toast}
	 * <p>
	 * This method may be called from any thread
	 * 
	 * @param activity
	 * @param message
	 */
	public static void show(final Activity activity, final String message) {
		if (activity == null)
			return;
		if (TextUtils.isEmpty(message))
			return;

		final Context context = activity.getApplication();
		activity.runOnUiThread(new Runnable() {

			public void run() {
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}
		});
	}

	/**
	 * Show the given message in a {@link Toast}
	 * <p>
	 * This method may be called from any thread
	 * 
	 * @param activity
	 * @param message
	 */
	public static void show(final Context context, final String message) {
		if (context == null)
			return;
		if (TextUtils.isEmpty(message))
			return;

		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

	}

	/**
	 * Show the message with the given resource id in a {@link Toast}
	 * <p>
	 * This method may be called from any thread
	 * 
	 * @param activity
	 * @param resId
	 */
	public static void show(final Activity activity, final int resId) {
		if (activity == null)
			return;

		show(activity, activity.getString(resId));
	}

	/**
	 * Show {@link Toast} for exception
	 * <p>
	 * This given default message will be used if an message can not be derived
	 * from the given {@link Exception}
	 * <p>
	 * This method may be called from any thread
	 * 
	 * @param activity
	 * @param e
	 */
	public static void show(Activity activity, Throwable e) {
		show(activity, e, R.string.unknown_error);
	}

	/**
	 * Show {@link Toast} for exception
	 * <p>
	 * This given default message will be used if an message can not be derived
	 * from the given {@link Exception}
	 * <p>
	 * This method may be called from any thread
	 * 
	 * @param activity
	 * @param e
	 * @param defaultMessage
	 */
	public static void show(final Activity activity, final Throwable e,
			final int defaultMessage) {
		if (activity == null)
			return;

		String message;
		if (e instanceof Throwable) {
			message = e.getMessage();
		} else {
			message = null;
		}

		if (TextUtils.isEmpty(message)) {
			message = activity.getString(defaultMessage);
		}

		show(activity, message);
	}
}
