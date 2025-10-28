<script setup>
import { computed, watch, ref, onMounted, onBeforeUnmount,shallowRef } from "vue";
import headerView from "../components/header.vue";
import footerView from "../components/footer.vue";
import { useScrollToTop } from "@/assets/base.js";
import { ElMessageBox, dialogEmits, ElMessage } from "element-plus";
const { showLoader, backtopStyle, handleMouseEnter, handleMouseLeave } =
  useScrollToTop();
import { Plus, Edit, Delete } from "@element-plus/icons-vue";
import { useTokenStore } from "@/stores/token";

import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import '@wangeditor/editor/dist/css/style.css' 

import { useRouter } from "vue-router";
const router = useRouter();
const tokenStore = useTokenStore();
const blogs = ref([]);

const currentPage = ref(1); //默认第一页
// 计算属性，确定是否显示上一页和下一页按钮
const showPreviousPage = computed(() => currentPage.value > 1);
const showNextPage = computed(() => currentPage.value < totalPages.value);
//一页显示的博客数量
const blogsPerPage = 5;
// 总共的博客数量
const totalBlogs = computed(() => blogs.value.length);
// 总页数
const totalPages = computed(() => Math.ceil(totalBlogs.value / blogsPerPage));
// 分页博客数据
const displayedBlogs = computed(() => {
  const start = (currentPage.value - 1) * blogsPerPage;
  const end = currentPage.value * blogsPerPage;
  return blogs.value.slice(start, end);
});
// 分页按钮
const changePage = (page) => {
  currentPage.value = page;
};

const drawer = ref(false);
const drawerTitile = ref("");
const drawerButton = ref("");
const blogData = ref({
  id: "",
  title: "",
  content: "",
  image: "",
});

//上传成功的回调函数
const uploadSuccess = (result) => {
  blogData.value.image = result.data;
};

import {
  userGetBlogListByUserIdService,
  userAddBlogService,
  userDelBlogService,
  userUpdateBlogService,
} from "@/api/blog.js";
// 添加博客
const addBlog = async () => {
  let result = await userAddBlogService(blogData.value);
  if (result.code == 1) {
    ElMessage({
      showClose: true,
      type: "success",
      message: result.msg ? result.msg : "添加成功",
      plain: true,
    });
    clearBlogData();
    drawer.value = false;
    getBlogList();
  } else {
    ElMessage.error(result.msg ? result.msg : "添加失败");
    clearBlogData();
  }
};

// 获取博客列表
const getBlogList = async () => {
  let result = await userGetBlogListByUserIdService();
  if (result.code == 1) {
    blogs.value = result.data;
  }
};
getBlogList();

//删除博客
const deleteBlog = async (id) => {
  ElMessageBox.confirm("你确认删除该文章吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await userDelBlogService(id);
      if (result.code === 1) {
        ElMessage({
          showClose: true,
          type: "success",
          message: result.msg ? result.msg : "删除成功",
          plain: true,
        });
        getBlogList();
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

// 编辑博客
const editeBlog = (row) => {
  blogData.value = row;
  drawer.value = true;
  drawerTitile.value = "编辑文章";
  drawerButton.value = "保存编辑";
};
//保存编辑
const saveEdit = async () => {
  let result = await userUpdateBlogService(blogData.value);
  if (result.code == 1) {
    ElMessage({
      showClose: true,
      type: "success",
      message: result.msg ? result.msg : "编辑成功",
      plain: true,
    });
    clearBlogData();
    drawer.value = false;
    getBlogList();
  } else {
    ElMessage.error(result.msg ? result.msg : "编辑失败");
    clearBlogData();
  }
};
// 查看博客详情
const goToBlogDetail = (id) => {
  router.push({ path: "/blogDetail", query: { id: id } });
};
// 清空数据
const clearBlogData = () => {
  blogData.value = {
    id: "",
    title: "",
    content: "",
    image: "",
  };
};

// 创建对话框宽度的响应式变量
const dialogWidth = ref("40%");

// 监听屏幕宽度变化
const mediaQuery = window.matchMedia("(max-width: 768px)");
const largeScreenQuery = window.matchMedia(
  "(min-width: 769px) and (max-width: 1400px)"
);

// 根据屏幕宽度设置对话框宽度
const setDialogWidth = () => {
  if (mediaQuery.matches) {
    dialogWidth.value = "90%";
  } else if (largeScreenQuery.matches) {
    dialogWidth.value = "60%";
  } else {
    dialogWidth.value = "40%";
  }
};

// 在组件挂载时添加监听器
onMounted(() => {
  setDialogWidth();
  mediaQuery.addListener(setDialogWidth);
  largeScreenQuery.addListener(setDialogWidth);
});

// 在组件卸载时移除监听器
onBeforeUnmount(() => {
  mediaQuery.removeListener(setDialogWidth);
  largeScreenQuery.removeListener(setDialogWidth);
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
});

/* wangEditor5 初始化开始 */
const editorRef = shallowRef()  // 编辑器实例，必须用 shallowRef
const mode = 'default'
const editorConfig = { MENU_CONF: {} }
// 图片上传配置
editorConfig.MENU_CONF['uploadImage'] = {
  headers: {
    Authorization: tokenStore.token,
  },
  server: "/api/user/file/wang/upload",  // 服务端图片上传接口
  fieldName: 'file'  // 服务端图片上传接口参数
}
// 记录 editor 实例，重要！
const handleCreated = (editor) => {
  editorRef.value = editor
}
/* wangEditor5 初始化结束 */
</script>


<template>
  <!-- <headerView /> -->

  <!-- breadcrumb -->
  <div class="container header-mt">
    <div class="row">
      <div class="col-12 d-flex justify-content-between align-items-center">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb custom-breadcrumb m-0">
            <li class="breadcrumb-item">
              <router-link to="/index"> 主页 </router-link>
            </li>
            <li class="breadcrumb-item" aria-current="page">
              <router-link to="/blog">
                <a href="">博客</a>
              </router-link>
            </li>
            <li class="breadcrumb-item active" aria-current="page">我的博客</li>
          </ol>
        </nav>
        <div>
          <el-button
            type="info"
            :icon="Plus"
            width="100%"
            text
            @click="
              (drawer = true),
                (drawerButton = '添加'),
                (drawerTitile = '发布文章'),
                clearBlogData()
            "
          >
            发布文章
          </el-button>
        </div>
      </div>
    </div>
  </div>
  <!-- end breadcrumb -->

  <el-dialog v-model="drawer" :width="dialogWidth">
    <template #header>
      <h4>{{ drawerTitile }}</h4>
    </template>
    <template #default>
      <el-form :model="blogData">
        <el-form-item label="标题">
          <el-input
            v-model="blogData.title"
            placeholder="请输入标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
            class="avatar-uploader"
            :auto-upload="true"
            action="/api/user/user/upload"
            name="file"
            :headers="{ Authorization: tokenStore.token }"
            :on-success="uploadSuccess"
            :show-file-list="false"
          >
            <img v-if="blogData.image" :src="blogData.image" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="详情" prop="content">
          <!-- <el-input
            type="textarea"
            v-model="blogData.content"
            maxlength="1000"
            show-word-limit
            rows="10"
          >
          </el-input> -->
          <div style="border: 1px solid #ccc; width: 100%">
            <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :mode="mode"
            />
            <Editor
                style="height: 500px; overflow-y: hidden;"
                v-model="blogData.content"
                :mode="mode"
                :defaultConfig="editorConfig"
                @onCreated="handleCreated"
            />
          </div>
        </el-form-item>
      </el-form>
    </template>
    <template #footer>
      <div class="custom-form__btn">
        <button
          class="btn submit-btn"
          @click="
            drawerTitile == '发布文章'
              ? addBlog()
              : drawerTitile == '编辑文章'
              ? saveEdit()
              : ElMessage.error('系统错误')
          "
        >
          {{ drawerButton }}
        </button>
      </div>
    </template>
  </el-dialog>
  <!-- main content -->
  <div class="main-content pb-80">
    <div class="container">
      <div class="row">
        <!-- blog post grid view -->
        <div class="col-12">
          <div
            class="row no-gutters blog-post blog-post--list mb-30"
            v-for="item in displayedBlogs"
            :key="item"
          >
            <div class="col-lg-4" @click="goToBlogDetail(item.id)">
              <div class="blog-post__img">
                <a href="javascript:void(0)">
                  <img :src="item.image" alt="" />
                </a>
              </div>
            </div>
            <div class="col-lg-8" >
              <div class="blog-post__inner ht-100">
                <div class="blog-post__inner--title" @click="goToBlogDetail(item.id)">
                  <a href="javascript:void(0)"
                    ><h4>{{ item.title }}</h4>
                  </a>
                </div>
                <div class="blog-post__inner--content">
                  <p v-html="item.content"></p>
                  <!-- <p>
                    {{ item.content }}
                  </p> -->
                </div>

                <div class="blog-post__inner--btn">
                  <el-button type="info" text size="small"
                    >发布时间：{{ item.createTime }}</el-button
                  >
                  <el-button
                    type="success"
                    :icon="Edit"
                    @click="editeBlog(item)"
                  >
                    编辑
                  </el-button>
                  <el-button
                    type="danger"
                    :icon="Delete"
                    @click="deleteBlog(item.id)"
                  >
                    删除
                  </el-button>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <!-- custom pagination -->
            <div class="col-12">
              <nav aria-label="Page navigation">
                <ul class="pagination custom-pagination">
                  <li class="page-item" v-if="currentPage > 1">
                    <a
                      class="page-link"
                      href="javascript:void(0)"
                      @click="changePage(currentPage - 1)"
                      ><i class="bi-caret-left-fill"></i
                    ></a>
                  </li>
                  <li
                    v-for="page in totalPages"
                    :key="page"
                    :class="{ 'page-item': true, active: currentPage === page }"
                  >
                    <a class="page-link" href="javascript:void(0)">
                      {{ page }}</a
                    >
                  </li>
                  <li class="page-item" v-if="currentPage < totalPages">
                    <a
                      class="page-link"
                      href="javascript:void(0)"
                      @click="changePage(currentPage + 1)"
                      ><i class="bi-caret-right-fill"></i
                    ></a>
                  </li>
                </ul>
              </nav>
            </div>
            <!-- end custom pagination -->
          </div>
        </div>
        <!-- end blog post grid view -->
      </div>
    </div>
  </div>
  <!-- end main content -->

  <!-- scroll up btn -->
  <el-backtop
    :style="backtopStyle"
    @mouseenter="handleMouseEnter"
    @mouseleave="handleMouseLeave"
  />
  <!-- end scroll up btn -->
  <!-- loader -->
  <div class="loader" v-if="showLoader">
    <div class="spinner">
      <div class="cube1"></div>
      <div class="cube2"></div>
    </div>
  </div>
  <!-- end loader -->

  <!-- <footerView /> -->
</template>


<style scoped lang="scss">
@import "@/assets/main.css";
a {
  text-decoration: none;
}
.media-body {
  -ms-flex: 1;
  flex: 1;
}
.media {
  display: -ms-flexbox;
  display: flex;
  -ms-flex-align: start;
  align-items: flex-start;
}

.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
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
.blog-post--list .blog-post__inner--content {
  height: 150px;
  overflow: auto;
  margin-bottom: 1rem;
}
</style>