package com.android.demo_app.helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



public class FragmentManagerUtils {

    public static String BASE_FRAGMENT = "base_fragment";

    public static void onChangeFragment(FragmentManager fragmentManager, int frameId, Fragment fragment, String tag) {
        FragmentManagerUtils.replaceFragmentCommon(fragmentManager, frameId, fragment, tag, false);
    }

    public static void replaceFragmentCommon(FragmentManager fragmentManager, int containerID, Fragment fragment, String tag, boolean isAddToBackStack) {
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0);

        if (!fragmentPopped && fragmentByTag == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(containerID, fragment, tag);
            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        } else {
            int index = fragmentManager.getBackStackEntryCount() - 1;
            if (index >= 0) {
                FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
                String foundTag = backEntry.getName();
                if (!tag.equals(BASE_FRAGMENT) && !tag.equals(foundTag))
                    fragmentManager.popBackStackImmediate(foundTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                else if (tag.equals(BASE_FRAGMENT))
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public static void replaceFragmentWithLeftRightAnimation(FragmentManager fragmentManager, int containerId, Fragment fragment, String tag, boolean isAddToBackStack,
                                                             boolean leftToRight) {

        if (isFragmentReplaced(fragmentManager, tag) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            /*if (leftToRight) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_left);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_to_left, R.anim.slide_from_right);
            }*/
            fragmentTransaction.replace(containerId, fragment, tag);

            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        }
    }

    public static Fragment isFragmentReplaced(FragmentManager fragmentManager, String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    public static void addFragmentCommon(FragmentManager fragmentManager, int containerID, Fragment fragment, String tag, boolean isAddtoBackStack) {
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);
        if (fragmentByTag == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().add(containerID, fragment, tag);
            if (isAddtoBackStack) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        } else {
            int index = fragmentManager.getBackStackEntryCount() - 1;
            if (index >= 0) {
                FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
                String foundTag = backEntry.getName();

                if (!tag.equals(BASE_FRAGMENT) && !tag.equals(foundTag))
                    fragmentManager.popBackStackImmediate(foundTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                else if (tag.equals(BASE_FRAGMENT))
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public static void popFragmentFromBackStack(FragmentManager fragmentManager, String tag) {
        int index = fragmentManager.getBackStackEntryCount() - 1;
        if (index >= 0) {
            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
            String foundTag = backEntry.getName();
            if (foundTag.equals(tag))
                fragmentManager.popBackStackImmediate();
        }
    }

}
