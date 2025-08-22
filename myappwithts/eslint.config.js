// eslint.config.js

import js from "@eslint/js";
import globals from "globals";
import tseslint from "typescript-eslint";
import pluginReact from "eslint-plugin-react";
import { defineConfig } from "eslint/config";
import pluginQuery from '@tanstack/eslint-plugin-query'

export default defineConfig([
  ...pluginQuery.configs['flat/recommended'],
  // JavaScript 및 TypeScript 파일에 적용될 규칙
  {
    files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"],
    plugins: { js },
    extends: ["js/recommended"],
    env: {
      browser: true,  // 브라우저 환경을 위한 전역 변수
      es2020: true,   // ECMAScript 2020 기능을 사용할 수 있도록 설정
      node: true      // Node.js 환경에서 사용하는 전역 변수들 활성화
    },
  },

  // 브라우저 환경에 글로벌 변수를 정의
  {
    files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"],
    languageOptions: { globals: globals.browser },
  },

  // TypeScript 설정을 위한 규칙 추가
  tseslint.configs.recommended,

  // React 관련 규칙 추가
  pluginReact.configs.recommended,

  // Prettier와의 충돌을 방지하는 설정 추가
  {
    files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"],
    extends: ["prettier"],
  },

  // 추가 ESLint 규칙 설정 (선택 사항)
  {
    files: ["**/*.{js,mjs,cjs,ts,jsx,tsx}"],
    rules: {
      "react/prop-types": "off", // prop-types 사용하지 않도록 설정 (TypeScript에서 대체 가능)
      "no-console": "warn", // console 사용을 경고로 표시
      "no-debugger": "warn", // debugger 사용을 경고로 표시
      "react/jsx-filename-extension": ["error", { extensions: [".jsx", ".tsx"] }], // JSX 파일 확장자 설정
      "prettier/prettier": ["error", { singleQuote: true, trailingComma: "es5" }], // Prettier 규칙 설정
    },
  },
]);
