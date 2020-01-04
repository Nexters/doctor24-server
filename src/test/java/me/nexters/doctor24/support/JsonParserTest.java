package me.nexters.doctor24.support;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class JsonParserTest {
    @Test
    void xml_to_json() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<response>\n" +
                "    <header>\n" +
                "        <resultCode>00</resultCode>\n" +
                "        <resultMsg>NORMAL SERVICE.</resultMsg>\n" +
                "    </header>\n" +
                "    <body>\n" +
                "        <items>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 남부순환로 2649, 베드로병원 (도곡동)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyMapimg>양재역 4번 출구 도보 5분 거리</dutyMapimg>\n" +
                "                <dutyName>강남베드로병원</dutyName>\n" +
                "                <dutyTel1>1544-7522</dutyTel1>\n" +
                "                <dutyTime1c>1800</dutyTime1c>\n" +
                "                <dutyTime1s>0900</dutyTime1s>\n" +
                "                <dutyTime2c>1800</dutyTime2c>\n" +
                "                <dutyTime2s>0900</dutyTime2s>\n" +
                "                <dutyTime3c>1800</dutyTime3c>\n" +
                "                <dutyTime3s>0900</dutyTime3s>\n" +
                "                <dutyTime4c>1800</dutyTime4c>\n" +
                "                <dutyTime4s>0900</dutyTime4s>\n" +
                "                <dutyTime5c>1800</dutyTime5c>\n" +
                "                <dutyTime5s>0900</dutyTime5s>\n" +
                "                <dutyTime6c>1400</dutyTime6c>\n" +
                "                <dutyTime6s>0900</dutyTime6s>\n" +
                "                <hpid>A1100141</hpid>\n" +
                "                <postCdn1>062</postCdn1>\n" +
                "                <postCdn2>71 </postCdn2>\n" +
                "                <rnum>1</rnum>\n" +
                "                <wgs84Lat>37.485612179925724</wgs84Lat>\n" +
                "                <wgs84Lon>127.0395873429168</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 도산대로 202 (논현동, 강남을지병원)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyMapimg>학동역 8번출구에서 도보 10분</dutyMapimg>\n" +
                "                <dutyName>강남을지대학교병원</dutyName>\n" +
                "                <dutyTel1>02-3438-1114</dutyTel1>\n" +
                "                <dutyTime1c>1730</dutyTime1c>\n" +
                "                <dutyTime1s>0830</dutyTime1s>\n" +
                "                <dutyTime2c>1730</dutyTime2c>\n" +
                "                <dutyTime2s>0830</dutyTime2s>\n" +
                "                <dutyTime3c>1730</dutyTime3c>\n" +
                "                <dutyTime3s>0830</dutyTime3s>\n" +
                "                <dutyTime4c>1730</dutyTime4c>\n" +
                "                <dutyTime4s>0830</dutyTime4s>\n" +
                "                <dutyTime5c>1730</dutyTime5c>\n" +
                "                <dutyTime5s>0830</dutyTime5s>\n" +
                "                <hpid>A1100234</hpid>\n" +
                "                <postCdn1>060</postCdn1>\n" +
                "                <postCdn2>47 </postCdn2>\n" +
                "                <rnum>2</rnum>\n" +
                "                <wgs84Lat>37.519406661927505</wgs84Lat>\n" +
                "                <wgs84Lon>127.02862813178366</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 언주로 631, 지하2층, 지상2층일부, 지상3층~6층 (논현동)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyName>강남지인병원</dutyName>\n" +
                "                <dutyTel1>02-6985-4119</dutyTel1>\n" +
                "                <dutyTime1c>1730</dutyTime1c>\n" +
                "                <dutyTime1s>0830</dutyTime1s>\n" +
                "                <dutyTime2c>1730</dutyTime2c>\n" +
                "                <dutyTime2s>0830</dutyTime2s>\n" +
                "                <dutyTime3c>1730</dutyTime3c>\n" +
                "                <dutyTime3s>0830</dutyTime3s>\n" +
                "                <dutyTime4c>1730</dutyTime4c>\n" +
                "                <dutyTime4s>0830</dutyTime4s>\n" +
                "                <dutyTime5c>1730</dutyTime5c>\n" +
                "                <dutyTime5s>0830</dutyTime5s>\n" +
                "                <dutyTime6c>1300</dutyTime6c>\n" +
                "                <dutyTime6s>0830</dutyTime6s>\n" +
                "                <hpid>A1120645</hpid>\n" +
                "                <postCdn1>061</postCdn1>\n" +
                "                <postCdn2>06 </postCdn2>\n" +
                "                <rnum>3</rnum>\n" +
                "                <wgs84Lat>37.5127915709</wgs84Lat>\n" +
                "                <wgs84Lon>127.0350275695</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 테헤란로 337 (역삼동, 4~9층)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyEtc>대표번호1 지역번호 추가20170125131431</dutyEtc>\n" +
                "                <dutyMapimg> 선릉역 6번 출구</dutyMapimg>\n" +
                "                <dutyName>강남초이스정형외과병원</dutyName>\n" +
                "                <dutyTel1>02-539-2250</dutyTel1>\n" +
                "                <dutyTime1c>2000</dutyTime1c>\n" +
                "                <dutyTime1s>0930</dutyTime1s>\n" +
                "                <dutyTime2c>1830</dutyTime2c>\n" +
                "                <dutyTime2s>0930</dutyTime2s>\n" +
                "                <dutyTime3c>1830</dutyTime3c>\n" +
                "                <dutyTime3s>0930</dutyTime3s>\n" +
                "                <dutyTime4c>1830</dutyTime4c>\n" +
                "                <dutyTime4s>0930</dutyTime4s>\n" +
                "                <dutyTime5c>1830</dutyTime5c>\n" +
                "                <dutyTime5s>0930</dutyTime5s>\n" +
                "                <dutyTime6c>1330</dutyTime6c>\n" +
                "                <dutyTime6s>0930</dutyTime6s>\n" +
                "                <hpid>A1117451</hpid>\n" +
                "                <postCdn1>061</postCdn1>\n" +
                "                <postCdn2>50 </postCdn2>\n" +
                "                <rnum>4</rnum>\n" +
                "                <wgs84Lat>37.504796716101495</wgs84Lat>\n" +
                "                <wgs84Lon>127.04845521564051</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 영동대로86길 9 (대치동, 지하1~2층,2~7층)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyInf>스포츠의학과, 척추관절</dutyInf>\n" +
                "                <dutyMapimg>삼성역 2번 출구</dutyMapimg>\n" +
                "                <dutyName>광혜병원</dutyName>\n" +
                "                <dutyTel1>02-538-7111</dutyTel1>\n" +
                "                <dutyTime1c>1830</dutyTime1c>\n" +
                "                <dutyTime1s>0930</dutyTime1s>\n" +
                "                <dutyTime2c>1830</dutyTime2c>\n" +
                "                <dutyTime2s>0930</dutyTime2s>\n" +
                "                <dutyTime3c>1830</dutyTime3c>\n" +
                "                <dutyTime3s>0930</dutyTime3s>\n" +
                "                <dutyTime4c>1830</dutyTime4c>\n" +
                "                <dutyTime4s>0930</dutyTime4s>\n" +
                "                <dutyTime5c>1830</dutyTime5c>\n" +
                "                <dutyTime5s>0930</dutyTime5s>\n" +
                "                <dutyTime6c>1300</dutyTime6c>\n" +
                "                <dutyTime6s>0930</dutyTime6s>\n" +
                "                <hpid>A1100113</hpid>\n" +
                "                <postCdn1>061</postCdn1>\n" +
                "                <postCdn2>74 </postCdn2>\n" +
                "                <rnum>5</rnum>\n" +
                "                <wgs84Lat>37.508137031122686</wgs84Lat>\n" +
                "                <wgs84Lon>127.06477119000925</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 도곡로 122 (도곡동, 삼우빌딩)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyEtc>산부인과(분만 X)/ 환자접수는 진료마감 30분까지 / 점심시간 13:00~13:30</dutyEtc>\n" +
                "                <dutyInf>대장전문병원</dutyInf>\n" +
                "                <dutyMapimg>3호선 양재역 3번출구 직진하다 뱅뱅사거리 우회전 200m오른편</dutyMapimg>\n" +
                "                <dutyName>기쁨병원</dutyName>\n" +
                "                <dutyTel1>02-570-1234</dutyTel1>\n" +
                "                <dutyTime1c>1700</dutyTime1c>\n" +
                "                <dutyTime1s>0830</dutyTime1s>\n" +
                "                <dutyTime2c>1700</dutyTime2c>\n" +
                "                <dutyTime2s>0830</dutyTime2s>\n" +
                "                <dutyTime3c>1700</dutyTime3c>\n" +
                "                <dutyTime3s>0830</dutyTime3s>\n" +
                "                <dutyTime4c>1700</dutyTime4c>\n" +
                "                <dutyTime4s>0830</dutyTime4s>\n" +
                "                <dutyTime5c>1700</dutyTime5c>\n" +
                "                <dutyTime5s>0830</dutyTime5s>\n" +
                "                <dutyTime6c>1300</dutyTime6c>\n" +
                "                <dutyTime6s>0830</dutyTime6s>\n" +
                "                <hpid>A1100156</hpid>\n" +
                "                <postCdn1>062</postCdn1>\n" +
                "                <postCdn2>59 </postCdn2>\n" +
                "                <rnum>6</rnum>\n" +
                "                <wgs84Lat>37.48992442128445</wgs84Lat>\n" +
                "                <wgs84Lon>127.03372331742996</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 언주로 731 (논현동)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyName>나누리병원</dutyName>\n" +
                "                <dutyTel1>1688-9797</dutyTel1>\n" +
                "                <dutyTime1c>1800</dutyTime1c>\n" +
                "                <dutyTime1s>0900</dutyTime1s>\n" +
                "                <dutyTime2c>1800</dutyTime2c>\n" +
                "                <dutyTime2s>0900</dutyTime2s>\n" +
                "                <dutyTime3c>1800</dutyTime3c>\n" +
                "                <dutyTime3s>0900</dutyTime3s>\n" +
                "                <dutyTime4c>1800</dutyTime4c>\n" +
                "                <dutyTime4s>0900</dutyTime4s>\n" +
                "                <dutyTime5c>1800</dutyTime5c>\n" +
                "                <dutyTime5s>0900</dutyTime5s>\n" +
                "                <dutyTime6c>1600</dutyTime6c>\n" +
                "                <dutyTime6s>0900</dutyTime6s>\n" +
                "                <hpid>A1119043</hpid>\n" +
                "                <postCdn1>060</postCdn1>\n" +
                "                <postCdn2>48 </postCdn2>\n" +
                "                <rnum>7</rnum>\n" +
                "                <wgs84Lat>37.5203062461</wgs84Lat>\n" +
                "                <wgs84Lon>127.033981326</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 선릉로 404, 누네안과병원 1층일부,2~8층 (대치동)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyMapimg>2호선 선릉역 2번출구 300m직진 도성초교 사거리 좌측위치</dutyMapimg>\n" +
                "                <dutyName>누네안과병원</dutyName>\n" +
                "                <dutyTel1>02-1661-1175</dutyTel1>\n" +
                "                <dutyTime1c>1730</dutyTime1c>\n" +
                "                <dutyTime1s>0900</dutyTime1s>\n" +
                "                <dutyTime2c>1730</dutyTime2c>\n" +
                "                <dutyTime2s>0900</dutyTime2s>\n" +
                "                <dutyTime3c>1730</dutyTime3c>\n" +
                "                <dutyTime3s>0900</dutyTime3s>\n" +
                "                <dutyTime4c>1730</dutyTime4c>\n" +
                "                <dutyTime4s>0900</dutyTime4s>\n" +
                "                <dutyTime5c>1730</dutyTime5c>\n" +
                "                <dutyTime5s>0900</dutyTime5s>\n" +
                "                <dutyTime6c>1300</dutyTime6c>\n" +
                "                <dutyTime6s>0900</dutyTime6s>\n" +
                "                <dutyTime7c>1300</dutyTime7c>\n" +
                "                <dutyTime7s>0900</dutyTime7s>\n" +
                "                <dutyTime8c>1300</dutyTime8c>\n" +
                "                <dutyTime8s>0900</dutyTime8s>\n" +
                "                <hpid>A1100161</hpid>\n" +
                "                <postCdn1>061</postCdn1>\n" +
                "                <postCdn2>98 </postCdn2>\n" +
                "                <rnum>8</rnum>\n" +
                "                <wgs84Lat>37.50098981107543</wgs84Lat>\n" +
                "                <wgs84Lon>127.05096790159399</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 논현로 705 (논현동)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyEtc>관절, 척추전문대표번호1 지역번호 추가20170125131431</dutyEtc>\n" +
                "                <dutyMapimg>7호선 학동역 7번출구</dutyMapimg>\n" +
                "                <dutyName>메드렉스병원</dutyName>\n" +
                "                <dutyTel1>02-6011-6000</dutyTel1>\n" +
                "                <dutyTime1c>1800</dutyTime1c>\n" +
                "                <dutyTime1s>0900</dutyTime1s>\n" +
                "                <dutyTime2c>1800</dutyTime2c>\n" +
                "                <dutyTime2s>0900</dutyTime2s>\n" +
                "                <dutyTime3c>1800</dutyTime3c>\n" +
                "                <dutyTime3s>0900</dutyTime3s>\n" +
                "                <dutyTime4c>1800</dutyTime4c>\n" +
                "                <dutyTime4s>0900</dutyTime4s>\n" +
                "                <dutyTime5c>1800</dutyTime5c>\n" +
                "                <dutyTime5s>0900</dutyTime5s>\n" +
                "                <dutyTime6c>1300</dutyTime6c>\n" +
                "                <dutyTime6s>0900</dutyTime6s>\n" +
                "                <hpid>A1100072</hpid>\n" +
                "                <postCdn1>060</postCdn1>\n" +
                "                <postCdn2>46 </postCdn2>\n" +
                "                <rnum>9</rnum>\n" +
                "                <wgs84Lat>37.5148977065</wgs84Lat>\n" +
                "                <wgs84Lon>127.0299747055</wgs84Lon>\n" +
                "            </item>\n" +
                "            <item>\n" +
                "                <dutyAddr>서울특별시 강남구 논현로 627 (논현동, 2~11층)</dutyAddr>\n" +
                "                <dutyDiv>B</dutyDiv>\n" +
                "                <dutyDivNam>병원</dutyDivNam>\n" +
                "                <dutyEmcls>G099</dutyEmcls>\n" +
                "                <dutyEmclsName>응급의료기관 이외</dutyEmclsName>\n" +
                "                <dutyEryn>2</dutyEryn>\n" +
                "                <dutyMapimg>언주역 : 2번 출구 도보 5분거리\n" +
                "학동역 : 4번 출구에서 480M (뉴힐탑호텔에서 200M)</dutyMapimg>\n" +
                "                <dutyName>비에비스나무병원</dutyName>\n" +
                "                <dutyTel1>1577-7502</dutyTel1>\n" +
                "                <dutyTime1c>1700</dutyTime1c>\n" +
                "                <dutyTime1s>0800</dutyTime1s>\n" +
                "                <dutyTime2c>1700</dutyTime2c>\n" +
                "                <dutyTime2s>0800</dutyTime2s>\n" +
                "                <dutyTime3c>1700</dutyTime3c>\n" +
                "                <dutyTime3s>0800</dutyTime3s>\n" +
                "                <dutyTime4c>1700</dutyTime4c>\n" +
                "                <dutyTime4s>0800</dutyTime4s>\n" +
                "                <dutyTime5c>1700</dutyTime5c>\n" +
                "                <dutyTime5s>0800</dutyTime5s>\n" +
                "                <dutyTime6c>1300</dutyTime6c>\n" +
                "                <dutyTime6s>0800</dutyTime6s>\n" +
                "                <hpid>A1100080</hpid>\n" +
                "                <postCdn1>061</postCdn1>\n" +
                "                <postCdn2>17 </postCdn2>\n" +
                "                <rnum>10</rnum>\n" +
                "                <wgs84Lat>37.50955952897761</wgs84Lat>\n" +
                "                <wgs84Lon>127.03235680386943</wgs84Lon>\n" +
                "            </item>\n" +
                "        </items>\n" +
                "        <numOfRows>10</numOfRows>\n" +
                "        <pageNo>1</pageNo>\n" +
                "        <totalCount>21</totalCount>\n" +
                "    </body>\n" +
                "</response>";
        JSONObject parse = JsonParser.parse(xml);
        System.out.println(parse);
    }
}