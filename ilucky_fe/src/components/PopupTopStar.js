/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable jsx-a11y/alt-text */
import React, { useEffect, useState } from "react";
import PopupBGTopStar from "../images/svgPopup/PopupHistory.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi, mainUrl } from "../util/api/requestUtils";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupTopStar = ({ onClose}) => {
  const [tab, setTab] = useState(1);
  const { t } = useTranslation();
  const [dataTab1, setDataTab1] = useState([]);
  const topNumber = 3;
  useEffect(() => {
    fetchData();
  },[]);


  const fetchData = async () => {
    try {
      const params = '?'+ new URLSearchParams({topNumber}).toString();  
      const url = mainUrl + "/api/top"+params
      const res = await callApi(url, "GET", {});
      setDataTab1(res || []);
    } catch (error) {
      console.log("error", error);
    } finally {
      //   setLoading(false);
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
            style={{
              position: "absolute",
              left: 134,
              color: "#4C2626",
            }}
          >
            {t("Rank")}
          </div>

          <div
            style={{
              position: "absolute",
              height: 387,
              width: "100%",
              top: 26,
              left: 4,
              padding: "30px 16px 16px 16px",
            }}
          >
            <div
              className="ct-flex-row"
              style={{
                justifyContent: "center",
                fontSize: 14,
                width: 300,
              }}
            >
              <div
                onClick={() => setTab(1)}
                className="btn-history-1 ct-flex-row"
                style={{
                  background:
                    tab === 1
                      ? "linear-gradient(180deg, #FED575 9.09%, #E1A920 49.12%, #FDD78D 92.3%)"
                      : "linear-gradient(180deg, #EEEEEE 9.09%, #D6D4D4 49.12%, #EEEEEE 92.3%)",
                }}
              >
                {t("Top Star")}
              </div>
            </div>
            <>
                <div
                  className="ct-flex-row"
                  style={{
                    justifyContent: "space-between",
                    width: 300,
                    marginTop: 10,
                    color: "#EBB859",
                    fontSize: 12,
                  }}
                >
                  <div>{t("No.")}</div>
                  <div style={{ marginRight: 80 }}> {t("Username")}</div>
                  <div>{t("Star")}</div>
                </div>
                <div
                  style={{
                    height: 280,
                    width: "90%",
                    overflow: "auto",
                  }}
                >
                  {dataTab1.map((item, index) => {
                    if(item == null) return null;
                    return (
                      <div
                        key={index.toString()}
                        className="ct-flex-row"
                        style={{
                          justifyContent: "space-between",
                          width: "96%",
                          fontSize: 13,
                          height: 25,
                          borderRadius: 3,
                          marginBottom: 8,
                          backgroundColor: "#B46C6C",
                          paddingRight: 6,
                          paddingLeft: 6,
                        }}
                      >
                        <div className="ct-flex-row">
                          <div>{index + 1}</div>
                        </div>
                        <div>{item.username}</div>
                        <div title={item.totalStar}>
                            {(
                                () => {
                                const str = item.totalStar.toString();
                                if (str.length > 6) {
                                  return `${str.substring(0, 6)}M`;
                                }
                                if (
                                    str.length <= 6 &&
                                    str.length > 3
                                ) {
                                  return `${str.substring(0, 3)}K`;
                                }
                                if (str.length <= 3) {
                                  return str;
                                }
                                } 
                            )()}
                          </div>
                      </div>
                    );
                  })}
                </div>
              </>
          </div>
          <img className="" style={{}} src={PopupBGTopStar} />
        </div>
      </div>
    </>
  );
};

export default PopupTopStar;
