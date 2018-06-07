package com.rowland.delivery.features.dash.presentation.fragments


import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.meetic.shuffle.Shuffle
import com.rowland.delivery.features.dash.di.modules.product.ProductModule
import com.rowland.delivery.features.dash.domain.models.product.Product
import com.rowland.delivery.features.dash.presentation.activities.DashActivity
import com.rowland.delivery.features.dash.presentation.viewmodels.product.NewProductViewModel
import com.rowland.delivery.merchant.R
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.content_product_images_shuffle.view.*
import kotlinx.android.synthetic.main.fragment_new_product.*
import java.util.*
import javax.inject.Inject


class NewProductFragment : Fragment() {

    private lateinit var newProductViewModel: NewProductViewModel

    @Inject
    lateinit var newProductFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance(args: Bundle?): Fragment {
            val frag = NewProductFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newProductViewModel = ViewModelProviders.of(this, newProductFactory).get(NewProductViewModel::class.java)

        if (arguments != null) {
            val category = arguments!!.getString("selected_category")
            newProductViewModel.setCategory(category!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_product, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as DashActivity).dashComponent.getProductComponent(ProductModule()).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(newproduct_toolbar)

        fab_addimage.setOnClickListener({ view ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RxPermissions(activity!!).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe { granted ->
                            if (!granted) {
                                Toast.makeText(activity, "Please grant permissions to select image", Toast.LENGTH_SHORT).show()
                            } else {
                                newProductViewModel.selectImages()
                            }
                        }
            }
        })

        btn_save.setOnClickListener({ view ->
            val product = Product()
            product.price = Integer.valueOf(input_product_pricing.text!!.toString())
            product.name = input_product_name.text!!.toString()
            product.saleQuantity = Integer.valueOf(input_product_quantity.text!!.toString())
            product.itemQuantity = Integer.valueOf(input_product_stock!!.text!!.toString())
            product.description = input_product_description.text!!.toString()
            product.createdAt = Date()

            newProductViewModel.saveProduct(product)
                    .subscribe({ newProduct ->
                        Toast.makeText(activity, "Product added succesfully", Toast.LENGTH_SHORT).show()
                        activity!!.supportFragmentManager.popBackStack(NewProductFragment::class.java.simpleName, POP_BACK_STACK_INCLUSIVE)
                    }) { throwable -> Toast.makeText(activity, "Product not added", Toast.LENGTH_SHORT).show() }

        })

        btn_cancell.setOnClickListener({ view ->
            activity!!.supportFragmentManager.popBackStack(NewProductFragment::class.java.simpleName, POP_BACK_STACK_INCLUSIVE)
        })
        new_product_shuffle.shuffleAdapter = ImageShuffleAdapter()
        //new_product_shuffle.viewAnimator = ShuffleViewAnimatorOnSecondCard()

        newProductViewModel.images.observe(this, Observer { uris ->
            (new_product_shuffle.shuffleAdapter as ImageShuffleAdapter).addAll(uris!!)
        })

    }

    class ImageShuffleViewHolder(itemView: View) : Shuffle.ViewHolder(itemView) {
        fun bind(uri: Uri, position: Int) {
            itemView!!.product_images_count!!.text = position.toString()
            val options = RequestOptions()
            options.centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(itemView!!.product_image.context)
                    .load(uri)
                    .apply(options)
                    .into(itemView!!.product_image)
        }
    }

    class ImageShuffleAdapter : Shuffle.Adapter<ImageShuffleViewHolder>() {

        private var uris: List<Uri> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup?, type: Int): ImageShuffleViewHolder {
            val view = LayoutInflater.from(parent!!.context).inflate(R.layout.content_product_images_shuffle, parent, false)
            return ImageShuffleViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: ImageShuffleViewHolder?, position: Int) {
            viewHolder!!.bind(this.uris.get(position), position)
        }

        override fun getItemCount(): Int {
            return this.uris.size
        }

        fun addAll(newUris: List<Uri>) {
            this.uris = newUris
            notifyDataSetChanged()
        }
    }
}
