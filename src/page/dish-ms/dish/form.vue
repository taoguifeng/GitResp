<template>
  <div>
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="form"
    >
      <el-form-item label-width="100px" label="菜品名称:" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>

      <el-form-item
        label-width="100px"
        label="菜品图片:"
        prop="img"
        v-loading="loading"
      >
        <el-input
          v-model="form.dishImg"
          placeholder="请填写图片URL，如您需要上传本地图片，请点击下方按钮上传"
        ></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="菜品单价:" prop="price">
        <el-input
          v-model.number="form.price"
          placeholder="请填写一个小数或整数，单位是元"
        ></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="菜品描述:" prop="desc">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请简要描述该菜品"
          v-model="form.desc"
        >
        </el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="菜品主料:" prop="mainIngredient">
        <el-input v-model="form.mainIngredient"></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="菜品辅料:" prop="ingredient">
        <el-input v-model="form.ingredient"></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="菜品分类:" prop="kind">
        <el-checkbox-group v-model="form.classificationIds">
          <el-checkbox v-for="c in cs" :label="c.id" :key="c.id">{{
            c.name
          }}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label-width="100px" label="制作方法:" prop="makeMethod">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请简要描述菜品制作方法"
          v-model="form.makeMethod"
        >
        </el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="菜品重量:" prop="weight">
        <el-input
          v-model.number="form.weight"
          placeholder="请输入一个整数或小数，单为是克(g)"
        ></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="制作时间:" prop="makeTime">
        <el-input v-model="form.makeTime"></el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="是否推荐: ">
        <el-checkbox-group v-model="form.marketingType" @change="test">
          <el-checkbox label="新品推荐"></el-checkbox>
          <el-checkbox label="菜品推荐"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label-width="100px" label="推荐理由:" prop="makeMethod">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请简要描述推荐理由(选填，当您选择菜品推荐时可选择填写此项，如果您不填写此项，则默认选择菜品描述作为原因)"
          v-model="form.reason"
        >
        </el-input>
      </el-form-item>

      <el-form-item label-width="100px" label="菜品标签:" prop="tags">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="多个标签请以空格或换行分隔，如：养生菜 下酒菜 适合小孩子吃"
          v-model="form.tags"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="优惠方式: " label-width="100px">
        <el-radio-group v-model="form.discount.type" size="medium">
          <el-radio-button border label="0">无优惠</el-radio-button>
          <el-radio-button border label="1">折扣优惠</el-radio-button>
          <el-radio-button border label="2">立减优惠</el-radio-button>
        </el-radio-group>
      </el-form-item>

      <el-form-item
        v-if="form.discount.type !== 0"
        label="优惠力度: "
        label-width="100px"
        prop="discountVal"
      >
        <el-input
          v-if="form.discount.type == 1"
          v-model.number="form.discount.val"
          placeholder="请输入折扣力度，输入应为一个 0 ~ 10 之间的数，例如输入 9 表示九折"
        ></el-input>
        <el-input
          v-if="form.discount.type == 2"
          v-model="form.discount.val"
          placeholder="请输入立减价格，输入应为一个整数或小数，单位为元"
        ></el-input>
      </el-form-item>

      <el-form-item
        v-if="form.discount.type !== 0"
        label="次数限制: "
        label-width="100px"
        prop="discountVal"
      >
        <el-input
          v-if="form.discount.type != 0"
          v-model.number="form.discount.count"
          placeholder="请输入用户对于该菜品每日最多可享受优惠的次数(次数每日凌晨两点刷新)，如果为 -1 表示不限制"
        ></el-input>
      </el-form-item>

      <el-form-item style="margin-left: 20px">
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {
  classification_list,
  createDish,
  updateDish,
  getDish,
} from "../../../api/dish";

export default {
  data() {
    return {
      form: {
        discount: {
          type: 0,
        },
        tags: "",
      },
      cs: [],
    };
  },
  methods: {
    fetchCsList() {
      classification_list().then((res) => {
        this.cs = res.arrays;
      });
    },
    onSubmit() {
      const submitForm = {
        ...this.form,
        tags: this.form.tags.toString().split(/s+/),
      };

      if (!this.form.id) {
        createDish(submitForm).then(() => {
          this.$message.success("创建菜品成功");
        });
      } else {
        updateDish(submitForm).then(() => {
          this.$message.success("修改菜品成功");
        });
      }
    },
  },

  created() {
    this.fetchCsList();
    if (this.$route.query.id) {
      getDish(this.$route.query.id).then((res) => {
        this.form = {
          ...res,
          discount: res.discount || {},
        };
      });
    }
  },
};
</script>

<style></style>
