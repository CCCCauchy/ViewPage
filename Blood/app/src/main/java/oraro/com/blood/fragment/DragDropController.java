//package oraro.com.blood.fragment;
//
//import android.view.View;
//
///**
// * Created by Administrator on 2016/9/26.
// */
//public class DragDropController {
//
//
//    public void handleDragHovered(View v, int x, int y) {
//        v.getLocationOnScreen(mLocationOnScreen);
//        final int screenX = x + mLocationOnScreen[0];
//        final int screenY = y + mLocationOnScreen[1];
//        final PhoneFavoriteSquareTileView view = mDragItemContainer.getViewForLocation(
//                screenX, screenY);
//        for (int i = 0; i < mOnDragDropListeners.size(); i++) {
//            mOnDragDropListeners.get(i).onDragHovered(screenX, screenY, view);
//        }
//    }
//
//    public void handleDragFinished(int x, int y, boolean isRemoveView) {
//        if (isRemoveView) {
//            for (int i = 0; i < mOnDragDropListeners.size(); i++) {
//                mOnDragDropListeners.get(i).onDroppedOnRemove();
//            }
//        }
//
//        for (int i = 0; i < mOnDragDropListeners.size(); i++) {
//            mOnDragDropListeners.get(i).onDragFinished(x, y);
//        }
//    }
//}
