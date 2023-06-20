package de.hhn.softwarelabor.pawswipeapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import de.hhn.softwarelabor.pawswipeapp.R
import java.util.Objects

/**
 * ViewPagerAdapter is a custom PagerAdapter for displaying images in a ViewPager.
 *
 * @property context The Context in which the ViewPagerAdapter is used.
 * @property imageList A list of image resource IDs to be displayed in the ViewPager.
 *
 * @author Leo Kalmbach
 */
class ViewPagerAdapter(val context: Context, private val imageList: List<Int>) : PagerAdapter() {

    /**
     * Returns the number of images in the ViewPager.
     *
     * @return The size of the imageList.
     */
    override fun getCount(): Int {
        return imageList.size
    }

    /**
     * Determines if a view is associated with a specific key object.
     *
     * @param view The View to be checked.
     * @param object The key object to be checked.
     * @return True if the view is associated with the key object, false otherwise.
     */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }


    /**
     * Creates a new View for the specified position in the ViewPager.
     *
     * @param container The ViewGroup that will contain the created View.
     * @param position The position in the ViewPager for which to create the View.
     * @return The created View for the specified position.
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView: View = mLayoutInflater.inflate(R.layout.image_slider_item, container, false)

        val imageView: ImageView = itemView.findViewById<View>(R.id.slideImageView) as ImageView

        imageView.setImageResource(imageList[position])

        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    /**
     * Removes a View from the specified position in the ViewPager.
     *
     * @param container The ViewGroup containing the View to be removed.
     * @param position The position of the View to be removed.
     * @param object The key object associated with the View to be removed.
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}