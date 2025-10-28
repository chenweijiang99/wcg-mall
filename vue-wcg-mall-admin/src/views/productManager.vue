<script setup>
import { Edit, Delete, Upload, Star } from "@element-plus/icons-vue";

import { ref, computed } from "vue";

//用户搜索时选中的分类id
const categoryId = ref("");

//用户搜索时选中的品牌
const brandId = ref("");

//商品列表数据模型
const products = ref([]);
//分类数据
const categorys = ref([]);
//品牌数据
const brands = ref([]);

//分页条数据模型
const pageNum = ref(1); //当前页
const pageSize = ref(5); //每页条数
// 当前页码

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
};
//计算分页后的商品列表
const displayProducts = computed(() => {
  if (products.value != null) {
    const startIndex = (pageNum.value - 1) * pageSize.value;
    const endIndex = startIndex + pageSize.value;
    return products.value.slice(startIndex, endIndex);
  } else {
    products.value = [];
  }
});

//回显文章分类
import {
  stopOrStartService,
  productListService,
  productUpdateService,
  productDeleteService,
  addProductService,
} from "@/api/product.js";

//获取商品列表数据
const productList = async () => {
  let result = await productListService();
  products.value = result.data;
};
productList();

const OLList = ref([]);
import { getOLList, addOL, deleteOL } from "@/api/officialCollection.js";
//获取官方收藏信息
const getOL = async () => {
  let result = await getOLList();
  OLList.value = result.data;
};
getOL();

//添加到官方收藏
const setOL = async (id) => {
  let result = await addOL(id);
  if (result.code == 1) {
    ElMessage.success("添加成功");
    getOL();
  } else if (result.code == 2) {
    ElMessage.error("添加失败，该商品已存在");
  }
};

//取消官方收藏
const unSetOL = async (id) => {
  let result = await deleteOL(id);
  if (result.code == 1) {
    ElMessage.success("取消成功");
    getOL();
  } else if (result.code == 2) {
    ElMessage.error("取消失败，该商品不存在");
  }
};

import { Plus } from "@element-plus/icons-vue";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
//控制抽屉是否显示
const visibleDrawer = ref(false);
const visibleDrawerCategory = ref(false);
const visibleDrawerBrand = ref(false);

//商品表单数据模型
const productModel = ref({
  id: "",
  image: "",
  detailImages: [],
  name: "",
  description: "",
  descriptionImage: [],
  price: "",
  inventory: "",
  categoryId: "",
  brandId: "",
  label: "",
  status: "",
});
const categoryModel = ref({
  id: "",
  name: "",
});
const brandModel = ref({
  id: "",
  name: "",
});
//清空表单数据模型
const clearProductModel = () => {
  productModel.value = {
    id: "",
    image: "",
    detailImages: [],
    name: "",
    description: "",
    descriptionImage: [],
    price: "",
    inventory: "",
    categoryId: "",
    brandId: "",
    label: "",
    status: "",
  };
  fileList.value = [];
  fileList1.value = [];
};
const clearCategoryModel = () => {
  categoryModel.value = {
    id: "",
    name: "",
  };
};
const clearBrandModel = () => {
  brandModel.value = {
    id: "",
    name: "",
  };
};
//导入token
import { useTokenStore } from "@/stores/token.js";
const tokenStore = useTokenStore();

//上传成功的回调函数
const uploadSuccess = (result) => {
  productModel.value.image = result.data;
  console.log(result.data);
};

const fileListUploadSuccess = (result) => {
  productModel.value.detailImages.push(result.data);
  getFileList(productModel.value.detailImages);
};

const fileList1UploadSuccess = (result) => {
  productModel.value.descriptionImage.push(result.data);
  getDescriptionFileList(productModel.value.descriptionImage);
};
import { ElMessageBox, ElMessage } from "element-plus";
//添加商品
const addProduct = async () => {
  productModel.value.detailImages = getDetailImages(fileList.value);
  productModel.value.descriptionImage = getDescriptionImages(fileList1.value);
  let result = await addProductService(productModel.value);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "添加成功");
    visibleDrawer.value = false;
    productList();
  } else {
    ElMessage.error(result.msg ? result.msg : "添加失败");
  }
};
// 编辑商品
const updateProduct = async () => {
  productModel.value.detailImages = getDetailImages(fileList.value);
  productModel.value.descriptionImage = getDescriptionImages(fileList1.value);
  let result = await productUpdateService(productModel.value);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "修改成功");
    visibleDrawer.value = false;
    productList();
  } else {
    ElMessage.error(result.msg ? result.msg : "修改失败");
  }
};
// 起售停售
const stopOrStart = async (row) => {
  let result = await stopOrStartService(row.id);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "修改成功");
    productList();
  } else {
    ElMessage.error(result.msg ? result.msg : "修改失败");
  }
};

//定义变量，控制标题
const title = ref("");
const button = ref("");
const titleCategory = ref("");
const buttonCategory = ref("");
const titleBrand = ref("");
const buttonBrand = ref("");

//隐藏按钮
const hideButton = ref(true);
//展示编辑弹窗
const showDialog = (row) => {
  fileList.value = [];
  visibleDrawer.value = true;
  title.value = "编辑商品";
  //数据拷贝
  productModel.value.id = row.id;
  productModel.value.image = row.image;
  if (row.detailImages) {
    productModel.value.detailImages = JSON.parse(row.detailImages);
    getFileList(productModel.value.detailImages);
  } else if (row.detailImages == null) {
    productModel.value.detailImages = [];
  }
  if (row.descriptionImage) {
    productModel.value.descriptionImage = JSON.parse(row.descriptionImage);
    getDescriptionFileList(productModel.value.descriptionImage);
  } else if (row.descriptionImage == null) {
    productModel.value.descriptionImage = [];
  }
  productModel.value.name = row.name;
  productModel.value.description = row.description;
  productModel.value.price = row.price;
  productModel.value.inventory = row.inventory;
  productModel.value.categoryId = row.categoryId;
  productModel.value.brandId = row.brandId;
  productModel.value.label = row.label;
  productModel.value.status = row.status;
};
// 展示编辑分类弹窗
const showDialogCategory = (row) => {
  visibleDrawerCategory.value = true;
  titleCategory.value = "编辑分类";
  //数据拷贝
  categoryModel.value.id = row.id;
  categoryModel.value.name = row.name;
};
//展示编辑品牌弹窗
const showDialogBrand = (row) => {
  visibleDrawerBrand.value = true;
  titleBrand.value = "编辑品牌";
  //数据拷贝
  brandModel.value.id = row.id;
  brandModel.value.name = row.name;
};

//删除商品
const deleteProduct = async (row) => {
  ElMessageBox.confirm("你确认删除该商品吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await productDeleteService(row.id);
      if (result.code === 1) {
        ElMessage.success(result.msg ? result.msg : "删除成功");
        productList();
      } else if (result.code === 0) {
        ElMessage.error(result.msg ? result.msg : "删除失败");
      }
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "用户取消删除",
      });
    });
};
import {
  getCategoryListService,
  updateCategoryService,
  addCategoryService,
  deleteCategoryService,
} from "@/api/category.js";
// 获取分类、
const getCategoryList = async () => {
  let result = await getCategoryListService();
  categorys.value = result.data;
};

getCategoryList();
// 新增分类
const addCategory = async () => {
  let result = await addCategoryService(categoryModel.value);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "添加成功");
    visibleDrawerCategory.value = false;
    getCategoryList();
  } else {
    ElMessage.error(result.msg ? result.msg : "添加失败");
  }
};

// 编辑分类
const updateCategory = async () => {
  let result = await updateCategoryService(categoryModel.value);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "修改成功");
    visibleDrawerCategory.value = false;
    getCategoryList();
  } else {
    ElMessage.error(result.msg ? result.msg : "修改失败");
  }
};

// 删除分类
const deleteCategory = async (row) => {
  ElMessageBox.confirm("你确认删除该分类吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await deleteCategoryService(row.id);
      if (result.code === 1) {
        ElMessage.success(result.msg ? result.msg : "删除成功");
        getCategoryList();
      } else if (result.code === 0) {
        ElMessage.error(result.msg ? result.msg : "删除失败");
      }
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "用户取消删除",
      });
    });
};

import {
  getBrandListService,
  updateBrandService,
  addBrandService,
  deleteBrandService,
} from "@/api/brand.js";
// 获取品牌
const getBrandList = async () => {
  let result = await getBrandListService();
  brands.value = result.data;
};
getBrandList();

// 编辑品牌
const updateBrand = async () => {
  let result = await updateBrandService(brandModel.value);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "修改成功");
    visibleDrawerBrand.value = false;
    getBrandList();
  } else {
    ElMessage.error(result.msg ? result.msg : "修改失败");
  }
};

// 新增品牌
const addBrand = async () => {
  let result = await addBrandService(brandModel.value);
  if (result.code === 1) {
    ElMessage.success(result.msg ? result.msg : "添加成功");
    visibleDrawerBrand.value = false;
    getBrandList();
  } else {
    ElMessage.error(result.msg ? result.msg : "添加失败");
  }
};

// 删除品牌
const deleteBrand = async (row) => {
  ElMessageBox.confirm("你确认删除该品牌吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await deleteBrandService(row.id);
      if (result.code === 1) {
        ElMessage.success(result.msg ? result.msg : "删除成功");
        getBrandList();
      } else if (result.code === 0) {
        ElMessage.error(result.msg ? result.msg : "删除失败");
      }
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "用户取消删除",
      });
    });
};

// 选择tab
const activeIndex = ref("1");
const handleSelect = (index) => {
  activeIndex.value = index;
};

const fileList = ref([]);
// 获取详情图文件列表
const getFileList = (detailImages) => {
  fileList.value = [];
  for (let i = 0; i < detailImages.length; i++) {
    fileList.value.push({
      url: detailImages[i],
    });
  }
};
// 获取详情图
const getDetailImages = (fileList) => {
  const detailImages = [];
  for (let i = 0; i < fileList.length; i++) {
    detailImages.push(fileList[i].url);
  }
  return JSON.stringify(detailImages);
};

const fileList1 = ref([]);
// 获取详细介绍图片文件列表
const getDescriptionFileList = (descriptionImage) => {
  fileList1.value = [];
  for (let i = 0; i < descriptionImage.length; i++) {
    fileList1.value.push({
      url: descriptionImage[i],
    });
  }
};
// 获取详细介绍图片
const getDescriptionImages = (fileList1) => {
  const descriptionImage = [];
  for (let i = 0; i < fileList1.length; i++) {
    descriptionImage.push(fileList1[i].url);
  }
  return JSON.stringify(descriptionImage);
};

const dialogImageUrl = ref("");
const dialogVisible = ref(false);
// 添加商品对话框中的详情图的删除事件
const handleRemove = (uploadFile, uploadFiles) => {
  fileList.value.find((item) => {
    if (item.uid === uploadFile.uid) {
      fileList.value.splice(fileList.value.indexOf(item), 1);
    }
  });
  productModel.value.detailImages = getDetailImages(fileList.value);
  console.log(uploadFile);
};
// 添加商品对话框中的详细介绍图片的删除事件
const handleRemove1 = (uploadFile, uploadFiles) => {
  fileList1.value.find((item) => {
    if (item.uid === uploadFile.uid) {
      fileList1.value.splice(fileList1.value.indexOf(item), 1);
    }
  });
  productModel.value.descriptionImage = getDetailImages(fileList1.value);
  console.log(uploadFile);
};
// 预览图片
const handlePreview = (file) => {
  dialogImageUrl.value = file.url;
  dialogVisible.value = true;
};

const search = async () => {
  ElMessage.error("暂未开放");
};
</script>
<template>
  <el-card class="page-container" v-if="activeIndex == 1">
    <template #header>
      <div class="header">
        <div class="header-left">
          <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
          >
            <el-menu-item index="1">商品管理</el-menu-item>
            <el-menu-item index="2">商品分类/品牌</el-menu-item>
          </el-menu>
        </div>

        <div class="extra">
          <el-button
            type="primary"
            @click="
              visibleDrawer = true;
              title = '添加商品';
              button = '添加';
              hideButton = true;
              clearProductModel();
            "
            >添加商品</el-button
          >
        </div>
      </div>
    </template>
    <!-- 搜索表单  #todo  暂未实现 -->
    <!-- <el-form inline>
      <el-form-item label="商品分类：">
        <el-select placeholder="请选择" v-model="categoryId">
          <el-option
            v-for="c in categorys"
            :key="c.id"
            :label="c.name"
            :value="c.id"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="商品品牌：">
        <el-select placeholder="请选择" v-model="brandId">
          <el-option
            v-for="b in brands"
            :key="b.id"
            :label="b.name"
            :value="b.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button
          @click="
            categoryId = '';
            brandId = '';
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form> -->
    <!-- 商品列表 -->
    <el-table :data="displayProducts" stripe>
      <el-table-column type="expand">
        <template #default="scope">
          <div style="display: flex; align-items: center">
            <div style="flex: 1">
              <h4 class="detail-title">商品详情图:</h4>
              <div style="display: flex">
                <el-image
                  v-for="(item, index) in JSON.parse(scope.row.detailImages)"
                  :key="index"
                  :src="item"
                  class="detail-image"
                ></el-image>
              </div>
            </div>
            <div style="flex: 1">
              <h4 class="detail-title">详情：</h4>
              <p class="detail-text">{{ scope.row.description }}</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="id" prop="id" width="50%;"></el-table-column>
      <el-table-column label="主图" prop="image">
        <template #default="scope">
          <el-image :src="scope.row.image" style="width: 50%"></el-image>
        </template>
      </el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="价格" prop="price"></el-table-column>
      <el-table-column label="库存" prop="inventory"></el-table-column>
      <el-table-column label="分类" prop="categoryId">
        <template #default="scope">
          <div v-for="c in categorys" :key="c.id">
            <div v-if="c.id == scope.row.categoryId">
              {{ c.name }}
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="品牌" prop="brandId">
        <template #default="scope">
          <div v-for="b in brands" :key="b.id">
            <div v-if="b.id == scope.row.brandId">
              {{ b.name }}
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="标签" prop="label"></el-table-column>
      <el-table-column label="状态" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.status == 1 ? 'success' : 'danger'">
            {{ scope.row.status == 1 ? "上架中" : "下架中" }}
          </el-tag>
          <el-button
            @click="stopOrStart(scope.row)"
            circle
            :type="scope.row.status == 1 ? 'info' : 'success'"
          >
            <el-icon v-if="scope.row.status == 1"><Moon /></el-icon>
            <el-icon v-if="scope.row.status == 0"><Sunny /></el-icon>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="收藏">
        <template #default="scope">
          <el-button
            :icon="Star"
            type="warning"
            @click="setOL(scope.row.id)"
            v-if="
              OLList.find((item) => item.productId == scope.row.id) == undefined
            "
            :disabled="OLList.length == 6"
            >设为收藏</el-button
          >
          <el-button
            :icon="Star"
            type="danger"
            @click="unSetOL(scope.row.id)"
            v-else
          >
            取消收藏
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="
              showDialog(row);
              button = '编辑';
              hideButton = false;
            "
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteProduct(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 分页条 -->
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      layout=" total, prev, pager, next"
      background
      :total="products.length"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />

    <!-- 抽屉 -->
    <el-drawer
      v-model="visibleDrawer"
      :title="title"
      direction="rtl"
      size="35%"
    >
      <!-- 添加商品表单 -->
      <el-form :model="productModel">
        <el-form-item label="商品名称">
          <el-input
            v-model="productModel.name"
            placeholder="请输入商品名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品价格">
          <el-input
            v-model="productModel.price"
            placeholder="请输入商品价格"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品库存">
          <el-input
            v-model="productModel.inventory"
            placeholder="请输入商品库存"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品标签">
          <el-input v-model="productModel.label" placeholder="请输入商品标签">
          </el-input>
        </el-form-item>
        <el-form-item label="商品分类">
          <el-select placeholder="请选择" v-model="productModel.categoryId">
            <el-option
              v-for="c in categorys"
              :key="c.id"
              :label="c.name"
              :value="c.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品品牌">
          <el-select placeholder="请选择" v-model="productModel.brandId">
            <el-option
              v-for="b in brands"
              :key="b.id"
              :label="b.name"
              :value="b.id"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="商品图片">
          <el-upload
            class="avatar-uploader"
            :auto-upload="true"
            action="/api/admin/user/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="uploadSuccess"
            :show-file-list="false"
          >
            <img
              v-if="productModel.image"
              :src="productModel.image"
              class="avatar"
            />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品详情图">
          <el-upload
            v-model:file-list="fileList"
            class="upload-demo"
            action="/api/admin/user/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="fileListUploadSuccess"
            :show-file-list="true"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            list-type="picture-card"
          >
            <el-button
              type="primary"
              :disabled="fileList.length >= 6"
              :icon="Upload"
            ></el-button>
          </el-upload>
          <el-dialog v-model="dialogVisible">
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>
        <el-form-item label="详细介绍图">
          <el-upload
            v-model:file-list="fileList1"
            class="upload-demo"
            action="/api/admin/user/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="fileList1UploadSuccess"
            :show-file-list="true"
            :on-preview="handlePreview"
            :on-remove="handleRemove1"
            list-type="picture-card"
          >
            <el-button type="info" :icon="Upload"></el-button>
          </el-upload>
          <el-dialog v-model="dialogVisible">
            <img w-full :src="dialogImageUrl" alt="Preview Image" />
          </el-dialog>
        </el-form-item>

        <el-form-item label="商品详情">
          <div class="editor">
            <el-input
              v-model="productModel.description"
              type="textarea"
              :maxlength="300"
              show-word-limit
              rows="10"
              placeholder="请输入商品详情"
            >
            </el-input>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="title == '添加商品' ? addProduct() : updateProduct()"
            >{{ button }}</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>
  <!-- 商品分类 -->
  <el-card class="page-container" v-if="activeIndex == 2">
    <template #header>
      <div class="header">
        <div class="header-left">
          <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
          >
            <el-menu-item index="1">商品管理</el-menu-item>
            <el-menu-item index="2">商品分类/品牌</el-menu-item>
            <!-- <el-menu-item index="3">商品品牌</el-menu-item> -->
          </el-menu>
        </div>
      </div>
    </template>
    <div style="display: flex; justify-content: space-around">
      <el-card style="width: 500px">
        <div class="extra">
          <el-button
            type="primary"
            @click="
              visibleDrawerCategory = true;
              titleCategory = '添加分类';
              buttonCategory = '添加';
              hideButton = true;
              clearCategoryModel();
            "
            >添加分类</el-button
          >
        </div>
        <el-table
          :data="categorys"
          stripe
          style="width: 500px; align-self: center"
        >
          <el-table-column label="分类ID" prop="id"></el-table-column>
          <el-table-column label="分类名称" prop="name"></el-table-column>
          <el-table-column label="操作" width="100">
            <template #default="{ row }">
              <el-button
                :icon="Edit"
                circle
                plain
                type="primary"
                @click="
                  showDialogCategory(row);
                  buttonCategory = '编辑';
                  hideButton = false;
                "
              ></el-button>
              <el-button
                :icon="Delete"
                circle
                plain
                type="danger"
                @click="deleteCategory(row)"
              ></el-button>
            </template>
          </el-table-column>
          <template #empty>
            <el-empty description="没有数据" />
          </template>
        </el-table>
      </el-card>

      <el-card style="width: 500px">
        <div class="extra">
          <el-button
            type="primary"
            @click="
              visibleDrawerBrand = true;
              titleBrand = '添加品牌';
              buttonBrand = '添加';
              hideButton = true;
              clearBrandModel();
            "
            >添加品牌</el-button
          >
        </div>
        <el-table :data="brands" stripe style="width: 500px">
          <el-table-column label="品牌ID" prop="id"></el-table-column>
          <el-table-column label="品牌名称" prop="name"></el-table-column>
          <el-table-column label="操作" width="100">
            <template #default="{ row }">
              <el-button
                :icon="Edit"
                circle
                plain
                type="primary"
                @click="
                  showDialogBrand(row);
                  buttonBrand = '编辑';
                  hideButton = false;
                "
              ></el-button>
              <el-button
                :icon="Delete"
                circle
                plain
                type="danger"
                @click="deleteBrand(row)"
              ></el-button>
            </template>
          </el-table-column>
          <template #empty>
            <el-empty description="没有数据" />
          </template>
        </el-table>
      </el-card>
    </div>
    <!-- 抽屉 -->
    <el-drawer
      v-model="visibleDrawerCategory"
      :title="titleCategory"
      direction="ltr"
      size="35%"
    >
      <!-- 添加分类表单 -->
      <el-form :model="categoryModel">
        <el-form-item label="分类名称">
          <el-input
            v-model="categoryModel.name"
            placeholder="请输入分类名称"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="
              titleCategory == '添加分类' ? addCategory() : updateCategory()
            "
            >{{ buttonCategory }}</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 抽屉 -->
    <el-drawer
      v-model="visibleDrawerBrand"
      :title="titleBrand"
      direction="rtl"
      size="35%"
    >
      <!-- 添加品牌表单 -->
      <el-form :model="brandModel">
        <el-form-item label="品牌名称">
          <el-input
            v-model="brandModel.name"
            placeholder="请输入品牌名称"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="titleBrand == '添加品牌' ? addBrand() : updateBrand()"
            >{{ buttonBrand }}</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>

  <!-- 商品品牌 -->
  <el-card class="page-container" v-if="activeIndex == 3">
    <template #header>
      <div class="header">
        <div class="header-left">
          <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
          >
            <el-menu-item index="1">商品管理</el-menu-item>
            <el-menu-item index="2">商品分类</el-menu-item>
            <el-menu-item index="3">商品品牌</el-menu-item>
          </el-menu>
        </div>

        <div class="extra">
          <el-button
            type="primary"
            @click="
              visibleDrawerBrand = true;
              titleBrand = '添加品牌';
              buttonBrand = '添加';
              hideButton = true;
              clearBrandModel();
            "
            >添加品牌</el-button
          >
        </div>
      </div>
    </template>
    <el-table :data="brands" stripe>
      <el-table-column label="品牌ID" prop="id"></el-table-column>
      <el-table-column label="品牌名称" prop="name"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="
              showDialogBrand(row);
              buttonBrand = '编辑';
              hideButton = false;
            "
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteBrand(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 抽屉 -->
    <el-drawer
      v-model="visibleDrawerBrand"
      :title="titleBrand"
      direction="rtl"
      size="35%"
    >
      <!-- 添加品牌表单 -->
      <el-form :model="brandModel">
        <el-form-item label="品牌名称">
          <el-input
            v-model="brandModel.name"
            placeholder="请输入品牌名称"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="titleBrand == '添加品牌' ? addBrand() : updateBrand()"
            >{{ buttonBrand }}</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>
</template>
<style lang="scss" scoped>
.hidden {
  display: none;
}
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .el-select {
    width: 150px;
  }
}

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
    .avatar1 {
      width: 78px;
      height: 78px;
      margin-right: 1rem;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}

::v-deep .el-input {
  width: 20rem;
}
.header-left {
  .title {
    margin-left: 1rem;
  }
  .active {
    color: var(--el-color-primary);
  }
  ::v-deep .el-menu {
    border: none;
  }
  ::v-deep .el-mune-item {
    border: none;
  }
}
.avatar1 {
  width: 78px;
  height: 78px;
  margin-right: 1rem;
  display: block;
}
.detail-image {
  width: 100px;
  height: 100px;
  margin-right: 0.5rem;
  margin-left: 0.5rem;
  box-shadow: #8c939d;
}
.detail-title {
  margin-left: 1rem;
}
.detail-text {
  margin-left: 1rem;
}
</style>