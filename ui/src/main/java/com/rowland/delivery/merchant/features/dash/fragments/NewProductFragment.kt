package com.rowland.delivery.merchant.features.dash.fragments

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.meetic.shuffle.Shuffle
import com.meetic.shuffle.ShuffleViewAnimatorOnSecondCard
import com.rowland.delivery.domain.models.product.ProductEntity
import com.rowland.delivery.merchant.R.string
import com.rowland.delivery.merchant.databinding.ContentProductImagesShuffleBinding
import com.rowland.delivery.merchant.databinding.FragmentNewProductBinding
import com.rowland.delivery.presentation.viewmodels.product.EditProductViewModel
import com.rowland.delivery.presentation.viewmodels.product.NewProductViewModel
import com.rowland.rxgallery.RxGallery
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Date

@AndroidEntryPoint
class NewProductFragment : Fragment() {

    private val newProductViewModel: NewProductViewModel by viewModels()
    private val args by navArgs<NewProductFragmentArgs>()

    private var _binding: FragmentNewProductBinding? = null
    private val binding get() = _binding!!

    companion object {

        fun newInstance(args: Bundle?): Fragment {
            val frag = NewProductFragment()
            frag.arguments = args
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddimage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                RxPermissions(requireActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (!granted) {
                            Toast.makeText(
                                activity,
                                getString(string.image_selection_perms_request),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            RxGallery.gallery(requireActivity(), true, RxGallery.MimeType.IMAGE)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    { uris ->
                                        newProductViewModel.setImageUri(uris)
                                    },
                                    { throwable ->
                                        Log.d(
                                            EditProductViewModel::class.java.simpleName,
                                            "Error Selecting Images: $throwable"
                                        )
                                    }) {
                                    Log.d(EditProductViewModel::class.java.simpleName, "Done Selecting Images")
                                }
                        }
                    }
            }
        }

        binding.btnSave.setOnClickListener {
            val product = ProductEntity()
            product.price = Integer.valueOf(binding.inputProductPricing.text.toString())
            product.name = binding.inputProductName.text!!.toString()
            product.saleQuantity = Integer.valueOf(binding.inputProductQuantity.text.toString())
            product.itemQuantity = Integer.valueOf(binding.inputProductStock.text.toString())
            product.description = binding.inputProductDescription.text.toString()
            product.createdAt = Date()

            newProductViewModel.createProduct(
                product,
                args.productCategory,
                FirebaseAuth.getInstance().currentUser!!.uid
            )
                .subscribe({
                    Toast.makeText(activity, getString(string.product_successfully_added), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }) { Toast.makeText(activity, getString(string.product_not_added), Toast.LENGTH_SHORT).show() }

        }

        binding.btnCancell.setOnClickListener {
            findNavController().popBackStack()
        }

        newProductViewModel.images.observe(viewLifecycleOwner, { uris ->
            binding.newProductShuffle.shuffleSettings.numberOfDisplayedCards = uris!!.size
            binding.newProductShuffle.shuffleAdapter = ImageShuffleAdapter(uris.map { uri -> Uri.parse(uri) })
            binding.newProductShuffle.viewAnimator = ShuffleViewAnimatorOnSecondCard()
        })
    }

    class ImageShuffleViewHolder(private val shuffleBinding: ContentProductImagesShuffleBinding) :
        Shuffle.ViewHolder(shuffleBinding.root) {

        fun bind(uri: Uri, position: Int) {
            this.shuffleBinding.productImagesCount.text = position.toString()
            val options = RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(shuffleBinding.productImage.context)
                .load(uri)
                .apply(options)
                .into(shuffleBinding.productImage)
        }
    }

    class ImageShuffleAdapter(_uris: List<Uri>) : Shuffle.Adapter<ImageShuffleViewHolder>() {

        var uris: List<Uri> = _uris

        override fun onCreateViewHolder(parent: ViewGroup, type: Int): ImageShuffleViewHolder {
            val binding = ContentProductImagesShuffleBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return ImageShuffleViewHolder(binding)
        }

        override fun onBindViewHolder(viewHolder: ImageShuffleViewHolder, position: Int) {
            viewHolder.bind(this.uris.get(position), position)
        }

        override fun getItemCount(): Int {
            return uris.size
        }
    }
}
