/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/alt-text */
import React, {useEffect, useState } from "react";
import PopupBGGetFreeTurn from "../images/svgPopup/PopupCancel.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi, mainUrl } from "../util/api/requestUtils";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupGetFreeTurn = ({ onClose }) => {
  const { t } = useTranslation();
  const [disabled, setDisabled] = useState(false);
  const [isAlreadyGetFree, setIsAlreadyGetFree] = useState("");


  useEffect(() => {
    isGetFreeTurnApi();
  },[]);

  const isGetFreeTurnApi = async () => {
    try {
      const url = mainUrl + "/api/user/getFreeTurn";
      const res = await callApi(url, "GET", {});
      console.log("res", res);
      if (res.status === "200_1") {
        setIsAlreadyGetFree("Nhan luot quay mien phi thanh cong");
      } else if(res.status === "200_0") {
        setIsAlreadyGetFree("Hom nay da nhan luot quay mien phi");
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  return (
    <>
      <div className="main-history ct-flex-col">
        <div
          style={{
            position: "relative",
            width: 330,
            height: gui.screenHeight,
            marginTop: 80,
          }}
        >
          <div
            onClick={onClose}
            style={{
              position: "absolute",
              zIndex: 10000,
              top: 10,
              right: -16,
              cursor: "pointer",
            }}
          >
            <img className="" src={IconX} />
          </div>
          <div
            className="title-popup"
            style={{
              position: "absolute",
              left: 120,
              top: 7,
              color: "#4C2626",
              fontSize: 14,
              width: "100px",
              textAlign: "center",
            }}
          >
            Gura
          </div>
          <div
            style={{
              position: "absolute",
              height: 130,
              width: "88%",
              top: 24,
              left: 8,
              padding: "24px 16px 0 16px",
              justifyContent: "center",
            }}
            className="ct-flex-col"
          >
            <div style={{ fontSize: 12, marginTop: 6 }}>
              {isAlreadyGetFree}
            </div>
          </div>
          <img className="" style={{}} src={PopupBGGetFreeTurn} />
        </div>
      </div>
    </>
  );
};

export default PopupGetFreeTurn;
